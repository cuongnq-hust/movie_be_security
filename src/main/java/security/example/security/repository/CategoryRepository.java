package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.model.CategoryMovie;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryMovie, Long> {
    CategoryMovie findCategoryMovieById(Long id);
}
