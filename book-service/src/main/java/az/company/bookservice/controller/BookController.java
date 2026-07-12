package az.company.bookservice.controller;

import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.request.UpdateBookRequest;
import az.company.bookservice.model.response.BookResponse;
import az.company.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(CREATED)
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        return bookService.createBook(createBookRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    @ResponseStatus(OK)
    public BookResponse updateBook(@Valid @RequestBody UpdateBookRequest updateBookRequest) {
        return bookService.updateBook(updateBookRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Page<BookResponse> getAllBooks(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String author
    ) {
        return bookService.getAllBooks(pageable, categoryId, author);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public List<BookResponse> getAllBooksWithAuthorAndTitle(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title
    ) {
        return bookService.getAllBooksWithAuthorAndTitle(author, title);
    }
}
