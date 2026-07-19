package az.company.bookservice.model.request;

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
public class CreateBookRequest {
    @NotBlank(message = Book_Title_Is_Required)
    private String title;
    @NotBlank(message = Book_Author_Is_Required)
    private String author;
    @NotBlank(message = Book_Description_Is_Required)
    private String description;
    @NotNull(message = Book_TotalCopies_Is_Required)
    private Integer totalCopies;
    private Year publishedYear;
    @NotNull(message = Book_CategoryId_Is_Required)
    private Long categoryId;
}
