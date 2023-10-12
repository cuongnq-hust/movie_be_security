package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import security.example.security.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
    @Query(value = """
        select * from reviews rv where rv.movie_id = :id
        """
            , countQuery = """
        select count(rv.id) from reviews rv where rv.moive_id = :id
        """, nativeQuery = true)
    List<Review> findReviewByMovieId(@Param("id") Long id);
}
