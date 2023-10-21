package security.example.security.service;

import security.example.security.dto.MovieDto;
import security.example.security.model.Movie;

import java.util.List;

public interface MovieService {
    Movie saveMovie(MovieDto movieDto);
    List<Movie> getAllMoview();

    Movie findMovieById(Long id);

    List<Movie> findMovieByName(String title);

    List<Movie> findMovieByCategoryId(Long id);
}
