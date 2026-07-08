package az.company.bookservice.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    CATEGORY_NOT_FOUND("Category not found with id: %s"),
    BOOK_NOT_FOUND("Book not found with id: %s"),
    CONFLICT("Available copies are not now for this book id: %s");
    private final String message;
}
