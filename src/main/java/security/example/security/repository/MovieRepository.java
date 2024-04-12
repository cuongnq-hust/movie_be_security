package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import security.example.security.entity.Movie;
import security.example.security.dto.movie.MovieSearchDto;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findMovieById(Long id);

    @Query("SELECT mo FROM Movie mo "
            + "WHERE mo.deleteFlag = 0 "
            + "AND (:#{#searchModel.title} IS NULL OR mo.title LIKE %:#{#searchModel.title}%)"
            + "ORDER BY mo.createdDate DESC")
    List<Movie> findListMovies(@Param("searchModel") MovieSearchDto searchModel);

    @Query(value = """
            SELECT * from MOVIE mv WHERE mv.CATEGORY_ID = :id
            AND mv.DELETE_FLAG = 0 ORDER BY mv.CREATED_DATE DESC 
            """
            , nativeQuery = true)
    List<Movie> findMovieByCategoryId(@Param("id") Long id);


}
