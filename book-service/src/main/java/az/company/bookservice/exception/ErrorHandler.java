package az.company.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handler(NotFoundException ex){
        return ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handler(Exception ex){
        return ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .build();
    }
}
