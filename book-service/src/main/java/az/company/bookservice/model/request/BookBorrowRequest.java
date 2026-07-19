package az.company.bookservice.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.company.bookservice.exception.constants.ApplicationConstants.BookId_Is_Required;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrowRequest {
    @NotNull(message = BookId_Is_Required)
    private Long bookId;

}
