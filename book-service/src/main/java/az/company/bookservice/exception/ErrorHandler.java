package az.company.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handler(NotFoundException ex){
        return ErrorResponse.builder()
                .status(NOT_FOUND.value())
                .message(ex.getCode())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handler(Exception ex){
        return ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handler(MethodArgumentNotValidException ex){
        return ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .message("VALIDATION_ERROR")
                .error(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handler(ConflictException ex){
        return ErrorResponse.builder()

                .status(CONFLICT.value())
                .message(ex.getCode())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
