package az.company.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        return ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
    }

    @ExceptionHandler(UserPresentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleUserPresentException(UserPresentException ex) {
        return ErrorResponse.builder()
                .code("USER_ALREADY_EXISTS")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return ErrorResponse.builder()
                .code("USER_NOT_FOUND")
                .message(ex.getMessage())
                .build();

    }
}
