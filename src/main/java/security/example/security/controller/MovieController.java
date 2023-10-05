package security.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Movie;
import security.example.security.model.Review;
import security.example.security.service.impl.MovieServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/createMovie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        System.out.println("movie la: " + movie.toString());
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @GetMapping("/allMovies")
    public List<Movie> allMovies() {
        return movieService.findAllMovies();
    }

    @GetMapping("/movie/{id}")
    public Movie findMovieById(@PathVariable Long id) {
        System.out.println("id cần tìm là: " + id);
        return movieService.findMovieById(id);
    }




}

