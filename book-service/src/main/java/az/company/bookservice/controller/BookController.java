package az.company.bookservice.controller;

import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.request.UpdateBookRequest;
import az.company.bookservice.model.response.BookResponse;
import az.company.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(CREATED)
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        return bookService.createBook(createBookRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(OK)
    public BookResponse updateBook(@Valid @RequestBody UpdateBookRequest updateBookRequest) {
        return bookService.updateBook(updateBookRequest);
    }

}
