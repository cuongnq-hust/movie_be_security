package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.entity.CategoryEntity;
import security.example.security.dto.category.SearchCategoryDto;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findCategoryMovieById(Long id);

    @Query("SELECT c FROM CategoryEntity c "
            + "WHERE c.deleteFlag = 0 "
            + "AND (:#{#searchModel.title} IS NULL OR c.title LIKE %:#{#searchModel.title}%)"
            + "ORDER BY c.createdDate DESC")
    List<CategoryEntity> searchListCategory(@Param("searchModel") SearchCategoryDto searchModel);
}
