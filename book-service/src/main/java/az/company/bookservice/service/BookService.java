package az.company.bookservice.service;

import az.company.bookservice.dao.repository.BookRepository;
import az.company.bookservice.dao.repository.CategoryRepository;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.mapper.BookMapper;
import az.company.bookservice.model.enums.BookStatus;
import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.request.UpdateBookRequest;
import az.company.bookservice.model.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.bookservice.exception.enums.ErrorStatus.CATEGORY_NOT_FOUND;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookResponse createBook(CreateBookRequest createBookRequest) {
       var category =  categoryRepository.findById(createBookRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                CATEGORY_NOT_FOUND.name(),
                        format(CATEGORY_NOT_FOUND.getMessage(), createBookRequest.getCategoryId()))    );


        var entity = BookMapper.mapToBookEntity(createBookRequest);
        entity.setCategory(category);
        bookRepository.save(entity);
        return BookMapper.mapToBookResponse(entity);
    }

    public BookResponse updateBook(UpdateBookRequest updateBookRequest) {
        var entity = bookRepository.findById(updateBookRequest.getId())
                .orElseThrow(() -> new NotFoundException(
                        CATEGORY_NOT_FOUND.name(),
                        format(CATEGORY_NOT_FOUND.getMessage(), updateBookRequest.getId())));

        entity.setTitle(updateBookRequest.getTitle());
        entity.setAuthor(updateBookRequest.getAuthor());
        entity.setDescription(updateBookRequest.getDescription());
        entity.setTotalCopies(updateBookRequest.getTotalCopies());
        entity.setPublishedYear(updateBookRequest.getPublishedYear());
        entity.setStatus(updateBookRequest.getStatus());
        bookRepository.save(entity);
        return BookMapper.mapToBookResponse(entity);
    }
}




