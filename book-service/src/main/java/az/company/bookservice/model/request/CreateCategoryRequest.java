package az.company.bookservice.model.request;

import az.company.bookservice.model.constants.ApplicationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.company.bookservice.model.constants.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = Category_Name_Is_Required)
    private String name;
    @NotBlank(message = Category_Description_Is_Required)
    private String description;
}
