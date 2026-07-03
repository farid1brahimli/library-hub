package az.company.bookservice.model.enums;

import lombok.Getter;

@Getter
public enum BookStatus {
    ACTIVE,
    INACTIVE,
    BORROWED,
    RETURNED,
    OVERDUE
}
