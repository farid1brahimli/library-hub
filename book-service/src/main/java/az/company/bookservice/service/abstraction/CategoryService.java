package az.company.bookservice.service.abstraction;

import az.company.bookservice.model.request.CreateCategoryRequest;
import az.company.bookservice.model.request.UpdateCategoryRequest;
import az.company.bookservice.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService{
    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
    CategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest) ;
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    void deleteCategory(Long id);

}
