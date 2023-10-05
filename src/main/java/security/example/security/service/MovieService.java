package security.example.security.service;

import security.example.security.model.Movie;
import security.example.security.model.Review;

import java.util.List;

public interface MovieService {
    Movie createMovie(Movie movie);

    Movie findMovieById(Long id);

    List<Movie> findAllMovies();

    List<Review> getReviewsByMovieId(Long movieId);
}
