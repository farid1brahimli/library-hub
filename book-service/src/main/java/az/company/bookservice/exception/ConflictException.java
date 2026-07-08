package az.company.bookservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConflictException extends RuntimeException {
    private final String code;
    public ConflictException(String code, String message) {
        super(message);
        this.code = code;
    }
}
