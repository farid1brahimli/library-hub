package az.company.bookservice.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    CATEGORY_NOT_FOUND("Category not found with id: %s"),
    BOOK_NOT_FOUND("Book not found with id: %s"),
    CONFLICT("Available copies are not now for this book id: %s"),
    BOOK_ALREADY_BORROWED("You have already borrowed this book: %s "),
    BOOK_ALREADY_CREATED("Book already exists with title and author: %s %s"),
    CATEGORY_ALREADY_CREATED("Category already exists with name: %s");
    private final String message;
}
