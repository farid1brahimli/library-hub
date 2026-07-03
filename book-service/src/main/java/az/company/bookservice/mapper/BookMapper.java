package az.company.bookservice.mapper;

import az.company.bookservice.dao.entity.BookEntity;
import az.company.bookservice.dao.entity.CategoryEntity;
import az.company.bookservice.model.enums.BookStatus;
import az.company.bookservice.model.request.CreateBookRequest;
import az.company.bookservice.model.response.BookResponse;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.UUID;

import static az.company.bookservice.model.enums.BookStatus.ACTIVE;
import static java.time.LocalDateTime.now;

public class BookMapper {
    public static BookEntity mapToBookEntity(CreateBookRequest createBookRequest) {
        return BookEntity.builder()
                .title(createBookRequest.getTitle())
                .author(createBookRequest.getAuthor())
                .isbn(UUID.randomUUID().toString())
                .description(createBookRequest.getDescription())
                .totalCopies(createBookRequest.getTotalCopies())
                .availableCopies(createBookRequest.getTotalCopies())
                .publishedYear(createBookRequest.getPublishedYear())
                .status(ACTIVE)
                .createdAt(now())
                .updatedAt(now())
                .build();
    }


    public static BookResponse mapToBookResponse(BookEntity bookEntity) {
        return BookResponse.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .isbn(bookEntity.getIsbn())
                .description(bookEntity.getDescription())
                .totalCopies(bookEntity.getTotalCopies())
                .availableCopies(bookEntity.getAvailableCopies())
                .publishedYear(bookEntity.getPublishedYear())
                .status(bookEntity.getStatus())
                .createdAt(bookEntity.getCreatedAt())
                .updatedAt(bookEntity.getUpdatedAt())
                .categoryName(bookEntity.getCategory().getName())
                .build();
    }

}
