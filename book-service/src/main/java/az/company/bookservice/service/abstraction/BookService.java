package az.company.bookservice.service.abstraction;

import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.request.UpdateBookRequest;
import az.company.bookservice.model.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponse createBook(CreateBookRequest createBookRequest);
    BookResponse updateBook(UpdateBookRequest updateBookRequest);
    void deleteBook(Long id);
    Page<BookResponse> getAllBooks(Pageable pageable, Long categoryId, String author);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooksWithAuthorAndTitle(String author, String title);
}
