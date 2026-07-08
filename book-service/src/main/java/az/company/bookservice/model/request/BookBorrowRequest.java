package az.company.bookservice.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.company.bookservice.model.constants.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrowRequest {
    @NotNull(message = UserId_Is_Required)
    private Long userId;
    @NotNull(message = BookId_Is_Required)
    private Long bookId;
}
