package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.example.security.model.CategoryMovie;
import security.example.security.model.Movie;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryMovie, Long> {
    CategoryMovie findCategoryMovieById(Long id);
    @Query(value = """
            select * from categorys ct
             """
            , countQuery = """
            select count(ct.id) from categorys ct
            where 1=1
            """
            , nativeQuery = true)
    List<CategoryMovie> findListCategory();
}
