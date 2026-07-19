package az.company.bookservice.service.abstraction;

import az.company.bookservice.model.request.BookBorrowRequest;
import az.company.bookservice.model.response.BorrowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowService {
    BorrowResponse borrow(BookBorrowRequest bookBorrowRequest, Long id);
    void returnBook(Long borrowId, Long userId);
    Page<BorrowResponse> getAllBorrows(Pageable pageable);
    void checkBorrowStatus();
    Page<BorrowResponse> getBorrowsByUserId(Long userId, Pageable pageable);

}
