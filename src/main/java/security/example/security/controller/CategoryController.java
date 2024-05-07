package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.category.CategorResponseDto;
import security.example.security.dto.category.CategoryRequestDto;
import security.example.security.entity.CategoryEntity;
import security.example.security.dto.category.SearchCategoryDto;
import security.example.security.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws Exception {
        categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<CategorResponseDto>> searchListCategory(@RequestBody SearchCategoryDto searchCategoryDto) throws Exception {
        List<CategorResponseDto> categorResponseDtos = categoryService.searchCategoryRedis(searchCategoryDto);
        if (categorResponseDtos == null) {
            List<CategorResponseDto> categorResponseDtoList = categoryService.searchListCategory(searchCategoryDto);
            categoryService.saveCategorys(searchCategoryDto, categorResponseDtoList);
            return new ResponseEntity<>(categorResponseDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categorResponseDtos, HttpStatus.OK);
        }
    }

    @PutMapping("/")
    public ResponseEntity<CategoryEntity> updateCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws Exception {
        categoryService.updateCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
