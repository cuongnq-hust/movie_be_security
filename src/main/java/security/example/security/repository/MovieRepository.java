package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import security.example.security.model.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findMovieById(Long id);

    @Query(value = """
            select * from movies mv
             """
            , countQuery = """
            select count(mv.id) from movies mv
            where 1=1
            """
            , nativeQuery = true)
    List<Movie> findListMovies();

    @Query(value = """
            select * from movies mv where mv.title LIKE %:title%
            """
            , countQuery = """
            select count(mv.id) from movies mv where mv.title LIKE %:title%
            """, nativeQuery = true)
    List<Movie> findMovieByTitle(@Param("title") String title);

    @Query(value = """
        select * from movies mv where mv.category_id = :id
        """
            , countQuery = """
        select count(mv.id) from movies mv where mv.category_id = :id
        """, nativeQuery = true)
    List<Movie> findMovieByCategoryId(@Param("id") Long id);


}
