package az.company.bookservice.mapper;

import az.company.bookservice.dao.entity.BookBorrowEntity;
import az.company.bookservice.model.response.BorrowResponse;

public class BorrowMapper {
    public static BorrowResponse mapToBorrowResponse(BookBorrowEntity entity) {
        return BorrowResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .bookId(entity.getBook().getId())
                .borrowedAt(entity.getBorrowedAt())
                .returnedAt(entity.getReturnedAt())
                .dueTime(entity.getDueTime())
                .status(entity.getStatus())
                .build();
    }
}
