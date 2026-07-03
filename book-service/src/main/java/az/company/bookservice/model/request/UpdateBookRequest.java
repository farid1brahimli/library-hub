package az.company.bookservice.model.request;

import az.company.bookservice.model.enums.BookStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    @NotNull(message = "Book id is not be empty")
    private Long id;
    @NotNull(message = "title id is not be empty")
    private String title;
    @NotNull(message = "author id is not be empty")
    private String author;
    @NotNull(message = "description id is not be empty")
    private String description;
    @NotNull(message = "totalCopies id is not be empty")
    private Integer totalCopies;
    @NotNull(message = "publishedYear id is not be empty")
    private Year publishedYear;
    @NotNull(message = "status id is not be empty")
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
