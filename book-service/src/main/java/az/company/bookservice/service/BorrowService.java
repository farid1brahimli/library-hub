package az.company.bookservice.service;

import az.company.bookservice.dao.entity.BookBorrowEntity;
import az.company.bookservice.dao.repository.BookRepository;
import az.company.bookservice.dao.repository.BorrowRepository;
import az.company.bookservice.exception.ConflictException;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.mapper.BorrowMapper;
import az.company.bookservice.model.dto.BorrowEvent;
import az.company.bookservice.model.request.BookBorrowRequest;

import az.company.bookservice.model.response.BorrowResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static az.company.bookservice.config.RabbitMQConfig.*;
import static az.company.bookservice.exception.enums.ErrorStatus.*;
import static az.company.bookservice.model.enums.BorrowStatus.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final RabbitTemplate rabbitTemplate;

    // region borrow method
    @Transactional
    public void borrow(BookBorrowRequest bookBorrowRequest, Long id) {
        var bookEntity = bookRepository.findById(bookBorrowRequest.getBookId()).orElseThrow(
                ()->new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        format(BOOK_NOT_FOUND.getMessage(), bookBorrowRequest.getBookId())
                ));

        if(bookEntity.getAvailableCopies() == 0) {
            throw new ConflictException(
                    CONFLICT.name(),
                    format(CONFLICT.getMessage(), bookEntity.getId())
            );
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        id = (Long) authentication.getPrincipal();

        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() - 1);
        var borrowEntity = BookBorrowEntity.builder()
                .userId(id)
                .book(bookEntity)
                .borrowedAt(now())
                .dueTime(now().plusDays(14).toLocalDate())
                .status(BORROWED)
                .build();
        bookRepository.save(bookEntity);
        borrowRepository.save(borrowEntity);

        BorrowEvent event = BorrowEvent.builder()
                .id(UUID.randomUUID().toString())
                .userId(borrowEntity.getUserId())
                .bookId(bookEntity.getId())
                .bookTitle(bookEntity.getTitle())
                .borrowedAt(borrowEntity.getBorrowedAt())
                .returnedAt(borrowEntity.getReturnedAt())
                .status(borrowEntity.getStatus())
                .build();

        rabbitTemplate.convertAndSend(
                BORROW_EXCHANGE,
                BORROW_ROUTING_KEY,
                event);
    }
    //endregion

    //region return method
    public void returnBook(Long id, Long bookId) {

        var borrowEntity = borrowRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        format(BOOK_NOT_FOUND.getMessage(), id)
                )
        );

        var bookEntity = bookRepository.findById(borrowEntity.getBook().getId()).orElseThrow(
                ()-> new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        format(BOOK_NOT_FOUND.getMessage(), borrowEntity.getBook().getId())
                )
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        bookId = (Long) authentication.getPrincipal();

        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + 1);
        bookRepository.save(bookEntity);
        borrowEntity.setStatus(RETURNED);
        borrowEntity.setReturnedAt(now());
        borrowRepository.save(borrowEntity);


        BorrowEvent event = BorrowEvent.builder()
                .id(UUID.randomUUID().toString())
                .userId(borrowEntity.getUserId())
                .bookId(bookId)
                .bookTitle(bookEntity.getTitle())
                .borrowedAt(borrowEntity.getBorrowedAt())
                .returnedAt(borrowEntity.getReturnedAt())
                .status(borrowEntity.getStatus())
                .build();

        rabbitTemplate.convertAndSend(
                BORROW_EXCHANGE,
                BORROW_ROUTING_KEY,
                event);
    }

    //endregion


    public Page<BorrowResponse> getAllBorrows(Pageable pageable) {
        return borrowRepository.findAll(pageable).map(BorrowMapper::mapToBorrowResponse);

    }

    public void checkBorrowStatus() {
        var list = borrowRepository.findAll();
        for (var borrowEntity : list) {
            if ((borrowEntity.getStatus() == BORROWED) && (LocalDate.now().isAfter(borrowEntity.getDueTime()))) {
                borrowEntity.setStatus(OVERDUE);

                borrowRepository.save(borrowEntity);

                BorrowEvent event = BorrowEvent.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(borrowEntity.getUserId())
                        .bookId(borrowEntity.getBook().getId())
                        .bookTitle(borrowEntity.getBook().getTitle())
                        .borrowedAt(borrowEntity.getBorrowedAt())
                        .returnedAt(borrowEntity.getReturnedAt())
                        .status(borrowEntity.getStatus())
                        .build();

                rabbitTemplate.convertAndSend(
                        BORROW_EXCHANGE,
                        BORROW_ROUTING_KEY,
                        event);
            }
        }
    }
}
