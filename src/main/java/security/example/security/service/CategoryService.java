package security.example.security.service;

import security.example.security.model.CategoryMovie;

import java.util.List;

public interface CategoryService {
    CategoryMovie saveCategory(String title);
    void  deleteCategoryById(Long id);
    CategoryMovie updateCategory(Long id, String title);

    List<CategoryMovie> getAllCategory();
}

