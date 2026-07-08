package az.company.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final String code;
    public UserNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
