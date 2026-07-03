package az.company.bookservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {

    @NotNull(message = "Category id cannot be blank")
    private Long categoryId;
    @NotBlank(message = "Category name cannot be blank")
    private String description;

}
