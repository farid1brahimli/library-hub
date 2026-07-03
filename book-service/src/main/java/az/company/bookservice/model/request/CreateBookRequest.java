package az.company.bookservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Title is not be empty")
    private String title;
    @NotBlank(message = "Author is not be empty")
    private String author;
    @NotBlank(message = "ISBN is not be empty")
    private String description;
    @NotNull(message = "Total copies is not be empty")
    private Integer totalCopies;
    private Year publishedYear;
    @NotNull(message = "Category id is not be empty")
    private Long categoryId;
}
