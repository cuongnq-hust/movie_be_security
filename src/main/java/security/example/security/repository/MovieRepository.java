package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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







}
