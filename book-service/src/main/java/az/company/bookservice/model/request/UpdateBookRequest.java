package az.company.bookservice.model.request;

import az.company.bookservice.model.enums.BookStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

import static az.company.bookservice.exception.constants.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    @NotNull(message = BookId_Is_Required)
    private Long id;
    @NotBlank(message = Book_Title_Is_Required)
    private String title;
    @NotBlank(message = Book_Author_Is_Required)
    private String author;
    @NotBlank(message = Book_Description_Is_Required)
    private String description;
    @NotNull(message = Book_TotalCopies_Is_Required)
    private Integer totalCopies;
    private Year publishedYear;
    @NotBlank(message = Book_Status_Is_Required)
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
