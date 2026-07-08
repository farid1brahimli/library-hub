package az.company.bookservice.service;

import az.company.bookservice.client.UserClient;
import az.company.bookservice.config.RabbitMQConfig;
import az.company.bookservice.dao.entity.BookBorrowEntity;
import az.company.bookservice.dao.repository.BookRepository;
import az.company.bookservice.dao.repository.BorrowRepository;
import az.company.bookservice.exception.ConflictException;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.model.dto.BorrowEvent;
import az.company.bookservice.model.enums.BookStatus;
import az.company.bookservice.model.enums.BorrowStatus;
import az.company.bookservice.model.request.BookBorrowRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static az.company.bookservice.config.RabbitMQConfig.*;
import static az.company.bookservice.exception.enums.ErrorStatus.*;
import static az.company.bookservice.model.enums.BorrowStatus.BORROWED;
import static az.company.bookservice.model.enums.BorrowStatus.RETURNED;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final UserClient userClient;
    private final BookRepository bookRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void borrow(BookBorrowRequest bookBorrowRequest) {
        var userResponse = userClient.getUser(bookBorrowRequest.getUserId());
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

        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() - 1);
        var borrowEntity = BookBorrowEntity.builder()
                .userId(userResponse.getId())
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

    public void returnBook(Long id) {

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

        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + 1);
        bookRepository.save(bookEntity);
        borrowEntity.setStatus(RETURNED);
        borrowEntity.setReturnedAt(now());
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
}
