package az.company.userservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.company.userservice.exception.constants.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfilRequest {
    @Size(max = 100, message = FULLNAME_SIZE_VALIDATION)
    private String fullName;
    @NotBlank(message = EMAIL_VALIDATION)
    @Email(message = EMAIL_VALIDATION)
    private String email;
}
