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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.bookservice.exception.enums.ErrorStatus.BOOK_NOT_FOUND;
import static az.company.bookservice.exception.enums.ErrorStatus.CATEGORY_NOT_FOUND;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @CacheEvict(value = "books", allEntries = true)
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

    @CacheEvict(value = "books", allEntries = true)
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

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        CATEGORY_NOT_FOUND.name(),
                        format(CATEGORY_NOT_FOUND.getMessage(), id)));

        entity.setStatus(BookStatus.INACTIVE);
        bookRepository.save(entity);
    }

    public Page<BookResponse> getAllBooks(Pageable pageable, Long categoryId, String author) {
        return bookRepository.findAll(pageable, categoryId, author)
                .map(BookMapper::mapToBookResponse);
    }

    @Cacheable(key = "#id", value = "books")
    public BookResponse getBookById(Long id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        format(BOOK_NOT_FOUND.getMessage(), id)));

        return BookMapper.mapToBookResponse(entity);
    }

    public List<BookResponse> getAllBooksWithAuthorAndTitle(String author, String title) {
        return bookRepository.findAllBooksWithAuthorAndTitle(author, title)
                .stream()
                .map(BookMapper::mapToBookResponse)
                .toList();
    }
}




