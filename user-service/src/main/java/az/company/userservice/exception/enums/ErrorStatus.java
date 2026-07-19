package az.company.userservice.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.IMessage;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    USER_ALREADY_EXISTS("User already exists with this username: %s"),
    USER_NOT_FOUND("User not found with this username: %s"),
    TOKEN_INVALID("Token invalid: "),
    REFRESH_TOKEN_IS_NOT_ACTIVE("Refresh token is not active: ");
    private final String message;
}
