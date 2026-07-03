package az.company.bookservice.mapper;

import az.company.bookservice.dao.entity.CategoryEntity;
import az.company.bookservice.model.request.CreateCategoryRequest;
import az.company.bookservice.model.response.CategoryResponse;

import java.time.LocalDateTime;

public class CategoryMapper {
    public static CategoryEntity mapToCategoryEntity(CreateCategoryRequest createCategoryRequest) {
        return CategoryEntity.builder()
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CategoryResponse mapToCategoryResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .createdAt(categoryEntity.getCreatedAt())
                .build();
    }
}
