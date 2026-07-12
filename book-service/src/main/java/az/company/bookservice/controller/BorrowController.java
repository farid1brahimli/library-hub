package az.company.bookservice.controller;

import az.company.bookservice.model.request.BookBorrowRequest;
import az.company.bookservice.model.response.BorrowResponse;
import az.company.bookservice.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @RequestMapping
    @ResponseStatus(CREATED)
    public void borrowBook(@RequestBody @Valid BookBorrowRequest bookBorrowRequest,@AuthenticationPrincipal Long id) {
        borrowService.borrow(bookBorrowRequest, id);
    }

    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable Long id, @AuthenticationPrincipal Long bookId) {
        borrowService.returnBook(id, bookId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<BorrowResponse> getAllBorrows(Pageable pageable) {
        return borrowService.getAllBorrows(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/overdue")
    public void checkOverdue() {
        borrowService.checkBorrowStatus();
    }

}
