package az.company.bookservice.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {
    public static final String Category_Name_Is_Required = "Category name is required";
    public static final String Category_Description_Is_Required = "Category description is required";
    public static final String Category_CategoryId_Is_Required = "Category categoryId is required";
    public static final String Book_Title_Is_Required = "Book title is required";
    public static final String Book_Author_Is_Required = "Book author is required";
    public static final String Book_Description_Is_Required = "Book description is required";
    public static final String Book_TotalCopies_Is_Required = "Book Total Copies is required";
    public static final String Book_CategoryId_Is_Required = "Book categoryId is required";
    public static final String Book_Status_Is_Required = "Book status is required";
    public static final String UserId_Is_Required = "Book borrow user id is required";
    public static final String BookId_Is_Required = "Book borrow book id is required";
}
