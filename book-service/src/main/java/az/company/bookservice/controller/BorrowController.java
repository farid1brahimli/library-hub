package az.company.bookservice.controller;

import az.company.bookservice.model.request.BookBorrowRequest;
import az.company.bookservice.service.BookService;
import az.company.bookservice.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @RequestMapping
    @ResponseStatus(CREATED)
    public void borrowBook(@RequestBody BookBorrowRequest bookBorrowRequest) {
        borrowService.borrow(bookBorrowRequest);
    }

    @PutMapping("/{id}")
    public void returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
    }

}
