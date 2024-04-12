package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import security.example.security.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);

    @Query(value = """
            SELECT * FROM REVIEW rv where rv.MOVIE_ID = :id
            AND rv.DELETE_FLAG = 0
            """, nativeQuery = true)
    List<Review> findReviewByMovieId(@Param("id") Long id);
}
