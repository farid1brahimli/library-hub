package az.company.userservice.model.response;

import az.company.userservice.model.enums.UserRoles;
import az.company.userservice.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id", "fullName", "username", "email", "roles", "createdAt", "status"})
public class UserResponse {
    private Long id;
    private String username;
    private UserStatus status;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<UserRoles> roles;
}
