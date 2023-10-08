package security.example.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import security.example.security.model.Movie;
import security.example.security.model.Review;

import java.util.List;

public interface MovieService {
    Movie createMovie(Movie movie);

    Movie findMovieById(Long id);

    List<Movie> findAllMovies() throws JsonProcessingException;

    List<Review> getReviewsByMovieId(Long movieId);
}
