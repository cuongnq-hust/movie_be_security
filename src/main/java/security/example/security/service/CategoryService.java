package security.example.security.service;

import security.example.security.dto.CategoryDto;
import security.example.security.model.CategoryMovie;

import java.util.List;

public interface CategoryService {
    CategoryMovie saveCategory(CategoryDto categoryDto);
    void  deleteCategoryById(Long id);
    CategoryMovie updateCategory(Long id, CategoryDto categoryDto);

    List<CategoryMovie> getAllCategory();
}

