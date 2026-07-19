package az.company.bookservice.service.concrete;

import az.company.bookservice.dao.repository.BookRepository;
import az.company.bookservice.dao.repository.CategoryRepository;
import az.company.bookservice.exception.BookAlreadyCreatedException;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.mapper.BookMapper;
import az.company.bookservice.model.enums.BookStatus;
import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.request.UpdateBookRequest;
import az.company.bookservice.model.response.BookResponse;
import az.company.bookservice.service.abstraction.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.bookservice.exception.enums.ErrorStatus.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookServiceHandler implements BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @CacheEvict(value = "books", allEntries = true)
    @Override
    public BookResponse createBook(CreateBookRequest createBookRequest) {
        if (bookRepository.findByTitleAndAuthor(createBookRequest.getTitle(), createBookRequest.getAuthor()).isPresent()) {
            throw new BookAlreadyCreatedException(
                    BOOK_ALREADY_CREATED.name(),
                    format(BOOK_ALREADY_CREATED.getMessage(),  createBookRequest.getTitle(), createBookRequest.getAuthor())
            );
        }

       var category =  categoryRepository.findById(createBookRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                CATEGORY_NOT_FOUND.name(),
                        format(CATEGORY_NOT_FOUND.getMessage(), createBookRequest.getCategoryId())));


        var entity = bookMapper.mapToBookEntity(createBookRequest);
        entity.setCategory(category);
        bookRepository.save(entity);
        return bookMapper.mapToBookResponse(entity);
    }

    @CacheEvict(value = "books", allEntries = true)
    @Override
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
        return bookMapper.mapToBookResponse(entity);
    }

    @CacheEvict(value = "books", allEntries = true)
    @Override
    public void deleteBook(Long id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        CATEGORY_NOT_FOUND.name(),
                        format(CATEGORY_NOT_FOUND.getMessage(), id)));

        entity.setStatus(BookStatus.INACTIVE);
        bookRepository.save(entity);
    }

    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable, Long categoryId, String author) {
        return bookRepository.findAll(pageable, categoryId, author)
                .map(bookMapper::mapToBookResponse);
    }

    @Cacheable(key = "#id", value = "books")
    @Override
    public BookResponse getBookById(Long id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        format(BOOK_NOT_FOUND.getMessage(), id)));

        return bookMapper.mapToBookResponse(entity);
    }

    @Override
    public List<BookResponse> getAllBooksWithAuthorAndTitle(String author, String title) {
        return bookRepository.findAllBooksWithAuthorAndTitle(author, title)
                .stream()
                .map(bookMapper::mapToBookResponse)
                .toList();
    }
}




