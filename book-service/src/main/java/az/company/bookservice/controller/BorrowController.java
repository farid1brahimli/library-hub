package az.company.bookservice.controller;

import az.company.bookservice.model.request.BookBorrowRequest;
import az.company.bookservice.model.response.BorrowResponse;
import az.company.bookservice.service.abstraction.BorrowService;
import az.company.bookservice.service.concrete.BorrowServiceHandler;
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
    public void returnBook(@PathVariable Long bookId, @AuthenticationPrincipal Long userId) {
        borrowService.returnBook(bookId, userId);
    }

    @GetMapping("/my")
    public Page<BorrowResponse> getMyBorrows(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return borrowService.getBorrowsByUserId(userId, pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<BorrowResponse> getAllBorrows(Pageable pageable) {

        return borrowService.getAllBorrows(pageable);
    }


}
