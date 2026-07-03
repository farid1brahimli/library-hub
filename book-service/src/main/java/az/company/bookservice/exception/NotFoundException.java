package az.company.bookservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String code;
    public NotFoundException(String code, String message) {

        super(message);
        this.code = code;
    }
}
