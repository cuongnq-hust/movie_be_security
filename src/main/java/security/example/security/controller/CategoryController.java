package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.CategoryMovie;
import security.example.security.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryServiceImpl categoryServicea;

    public CategoryController(CategoryServiceImpl categoryServicea) {
        this.categoryServicea = categoryServicea;
    }
    @PostMapping("/new")
    public ResponseEntity<CategoryMovie> createCategory(@RequestBody String title)  {
        CategoryMovie addNewCategory = categoryServicea.saveCategory(title);
        return ResponseEntity.status(HttpStatus.CREATED).body(addNewCategory);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id)  {
        categoryServicea.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<CategoryMovie> updateCategory(@RequestBody String title, @PathVariable Long id)  {
        CategoryMovie updateCategory = categoryServicea.updateCategory(id, title);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateCategory);
    }
}
