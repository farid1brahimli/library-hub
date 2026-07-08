package az.company.userservice.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {
public static final String USERNAME_VALIDATION = "Username is required";
public static final String EMAIL_VALIDATION = "Email is required";
public static final String PASSWORD_VALIDATION = "Password is required";
public static final String USERNAME_SIZE_VALIDATION = "Username size has to be between 3 and 50 character";
public static final String PASSWORD_SIZE_VALIDATION = "Password size has to be min 8 character";
public static final String FULLNAME_SIZE_VALIDATION = "Fullname size has to be max 100 character";
}
