package security.example.security.service;

import security.example.security.model.CategoryMovie;

public interface CategoryService {
    CategoryMovie saveCategory(String title);
    void  deleteCategoryById(Long id);
    CategoryMovie updateCategory(Long id, String title);
}
