package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.CategoryDto;
import security.example.security.model.CategoryMovie;
import security.example.security.service.impl.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/new")
    public ResponseEntity<CategoryMovie> createCategory(@RequestBody CategoryDto categoryDto)  {
        CategoryMovie addNewCategory = categoryService.saveCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addNewCategory);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id)  {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<CategoryMovie> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id)  {
        CategoryMovie updateCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateCategory);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryMovie>> getAllCategory(){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.getAllCategory());
    }
}
