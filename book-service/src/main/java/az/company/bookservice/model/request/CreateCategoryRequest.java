package az.company.bookservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = "Category name cannot be blank")
    private String name;
    @NotBlank(message = "Category description cannot be blank")
    private String description;
}
