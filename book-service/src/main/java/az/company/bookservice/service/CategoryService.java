package az.company.bookservice.service;

import az.company.bookservice.dao.repository.CategoryRepository;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.exception.enums.ErrorStatus;
import az.company.bookservice.mapper.CategoryMapper;
import az.company.bookservice.model.request.CreateCategoryRequest;
import az.company.bookservice.model.request.UpdateCategoryRequest;
import az.company.bookservice.model.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.bookservice.exception.enums.ErrorStatus.CATEGORY_NOT_FOUND;
import static az.company.bookservice.mapper.CategoryMapper.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private CategoryResponse categoryResponse;

    public CategoryResponse createCategory(CreateCategoryRequest  createCategoryRequest) {
        var entity = mapToCategoryEntity(createCategoryRequest);
        categoryRepository.save(entity);
        return mapToCategoryResponse(entity);
    }

    public CategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        var entity = categoryRepository.findById(updateCategoryRequest.getCategoryId())
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), updateCategoryRequest.getCategoryId())));

        entity.setDescription(updateCategoryRequest.getDescription());
        categoryRepository.save(entity);
        return mapToCategoryResponse(entity);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), id)));

        return mapToCategoryResponse(entity);
    }

    public void deleteCategory(Long id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), id)));

        categoryRepository.delete(entity);
    }
}

