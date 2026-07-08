package az.company.userservice.model.request;

import az.company.userservice.exception.constants.ApplicationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.company.userservice.exception.constants.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = USERNAME_VALIDATION)
    @Size(min = 3, max = 50, message = USERNAME_SIZE_VALIDATION)
    private String username;
    @NotBlank(message = EMAIL_VALIDATION)
    private String email;
    @NotBlank(message = PASSWORD_VALIDATION)
    @Size(min = 8, message = PASSWORD_SIZE_VALIDATION)
    private String password;
    @Size(max = 100, message = FULLNAME_SIZE_VALIDATION)
    private String fullName;

}
