package az.company.bookservice.controller;

import az.company.bookservice.model.request.BookBorrowRequest;
import az.company.bookservice.model.response.BorrowResponse;
import az.company.bookservice.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
    }

    @GetMapping
    public Page<BorrowResponse> getAllBorrows(Pageable pageable) {
        return borrowService.getAllBorrows(pageable);
    }

    @PutMapping("/overdue")
    public void checkOverdue() {
        borrowService.checkBorrowStatus();
    }

}
