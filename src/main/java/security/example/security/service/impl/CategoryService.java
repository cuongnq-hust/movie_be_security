package security.example.security.service.impl;

import org.springframework.stereotype.Service;
import security.example.security.dto.category.CategorResponseDto;
import security.example.security.dto.category.CategoryRequestDto;
import security.example.security.dto.movie.MovieRequestDto;
import security.example.security.dto.movie.MovieSearchDto;
import security.example.security.entity.CategoryEntity;
import security.example.security.dto.category.SearchCategoryDto;
import security.example.security.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import security.example.security.service.RedisService;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RedisService<CategorResponseDto> redisService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public CategoryService(CategoryRepository categoryRepository, RedisService<MovieRequestDto> redisService, RedisService<CategorResponseDto> redisService1) {
        this.categoryRepository = categoryRepository;
        this.redisService = redisService1;
    }

    public void createCategory(CategoryRequestDto categoryRequestDto) throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setTitle(categoryRequestDto.getTitle());
        categoryRepository.save(category);
        redisService.deleteKeysStartingWith("category");
    }

    public List<CategorResponseDto> searchListCategory(SearchCategoryDto searchCategoryDto) {
        List<CategoryEntity> categoryEntityList = categoryRepository.searchListCategory(searchCategoryDto);
        return categoryEntityList.stream().map(item -> modelMapper.map(item, CategorResponseDto.class))
                .collect(Collectors.toList());
    }

    public void updateCategory(CategoryRequestDto categoryRequestDto) throws Exception {
        CategoryEntity categoryMovie = categoryRepository.findCategoryMovieById(categoryRequestDto.getId());
        categoryMovie.setTitle(categoryRequestDto.getTitle());
        categoryRepository.save(categoryMovie);
        redisService.deleteKeysStartingWith("category");
    }

    public void deleteCategoryById(Long id) throws Exception {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            CategoryEntity category = optionalCategory.get();
            category.setDeleteFlag(1);
            categoryRepository.save(category);
            redisService.deleteKeysStartingWith("category");
        }
    }

    private String getKeyFrom(SearchCategoryDto searchCategoryDto) {
        StringBuilder keyBuilder = new StringBuilder("category:");
        if (searchCategoryDto.getTitle() != null) {
            keyBuilder.append(searchCategoryDto.getTitle()).append(":");
        }
        return keyBuilder.toString();
    }

    public List<CategorResponseDto> searchCategoryRedis(SearchCategoryDto searchCategoryDto) throws Exception {
        String key = getKeyFrom(searchCategoryDto);
        return redisService.searchList(key);
    }

    public void saveCategorys(SearchCategoryDto searchCategoryDto, List<CategorResponseDto> categorResponseDtos) throws Exception {
        String key = getKeyFrom(searchCategoryDto);
        redisService.saveList(key, categorResponseDtos);
    }
}
