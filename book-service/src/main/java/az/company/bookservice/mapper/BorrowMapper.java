package az.company.bookservice.mapper;

import az.company.bookservice.dao.entity.BookBorrowEntity;
import az.company.bookservice.model.response.BorrowResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowMapper {
    @Mapping(target = "bookId", source = "book.id")
    BorrowResponse mapToBorrowResponse(BookBorrowEntity entity);


}