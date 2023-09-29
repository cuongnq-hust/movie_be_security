package security.example.security.service;

import security.example.security.model.Movie;

import java.util.List;

public interface MovieService {
    Movie createMovie(Movie movie);

    List<Movie> findAllMovies();
}
