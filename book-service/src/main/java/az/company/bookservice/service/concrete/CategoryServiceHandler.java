package az.company.bookservice.service.concrete;

import az.company.bookservice.dao.repository.CategoryRepository;
import az.company.bookservice.exception.CategoryAlreadyCreatedException;
import az.company.bookservice.exception.NotFoundException;
import az.company.bookservice.mapper.CategoryMapper;
import az.company.bookservice.model.request.CreateCategoryRequest;
import az.company.bookservice.model.request.UpdateCategoryRequest;
import az.company.bookservice.model.response.CategoryResponse;
import az.company.bookservice.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.bookservice.exception.enums.ErrorStatus.*;
import static az.company.bookservice.mapper.CategoryMapper.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CategoryServiceHandler implements CategoryService {
    private final CategoryRepository categoryRepository;
    private CategoryResponse categoryResponse;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest  createCategoryRequest) {

        if(categoryRepository.findByName(createCategoryRequest.getName()).isPresent()) {
            throw new CategoryAlreadyCreatedException(
                    CATEGORY_ALREADY_CREATED.name(),
                    format(CATEGORY_ALREADY_CREATED.getMessage(),  createCategoryRequest.getName()));

        }

        var entity = mapToCategoryEntity(createCategoryRequest);
        categoryRepository.save(entity);
        return mapToCategoryResponse(entity);
    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        var entity = categoryRepository.findById(updateCategoryRequest.getCategoryId())
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), updateCategoryRequest.getCategoryId())));

        entity.setDescription(updateCategoryRequest.getDescription());
        categoryRepository.save(entity);
        return mapToCategoryResponse(entity);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), id)));

        return mapToCategoryResponse(entity);
    }

    @Override
    public void deleteCategory(Long id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(CATEGORY_NOT_FOUND.name(),
                                format(CATEGORY_NOT_FOUND.getMessage(), id)));

        categoryRepository.delete(entity);
    }
}

