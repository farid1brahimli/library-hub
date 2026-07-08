package az.company.userservice.exception;

public class UserPresentException extends RuntimeException {
    public UserPresentException(String message, String format) {
        super(message);
    }
}
