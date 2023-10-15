package security.example.security.service.impl;

import org.springframework.stereotype.Service;
import security.example.security.model.CategoryMovie;
import security.example.security.repository.CategoryRepository;
import security.example.security.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryMovie saveCategory(String title) {
        CategoryMovie categoryMovie1 = new CategoryMovie();
        categoryMovie1.setTitle(title);
        System.out.println("Them Category Thanh Cong");
        return categoryRepository.save(categoryMovie1);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
        System.out.println("Xoa category thanh cong");
    }

    @Override
    public CategoryMovie updateCategory(Long id, String title) {
        CategoryMovie categoryMovie = categoryRepository.findCategoryMovieById(id);
        categoryMovie.setTitle(title);
        System.out.println("Update Thanh Cong Category");
        return categoryRepository.save(categoryMovie);
    }

    @Override
    public List<CategoryMovie> getAllCategory() {
        return categoryRepository.findListCategory();
    }
}
