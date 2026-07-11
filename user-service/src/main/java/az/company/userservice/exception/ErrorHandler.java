package az.company.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        return ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message("INTERNAL_SERVER_ERROR")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .message("VALIDATION_ERROR")
                .error(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UserPresentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleUserPresentException(UserPresentException ex) {
        return ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .message("USER_ALREADY_EXISTS")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .message("USER_NOT_FOUND")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

    }
}
