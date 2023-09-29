package security.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Movie;
import security.example.security.service.impl.MovieServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/createMovie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        System.out.println("movie la: " +movie.toString());
        return ResponseEntity.ok(movieService.createMovie(movie));
    }
    @GetMapping("/allMovies")
    public ResponseEntity<List<Movie>> allMovies(){
        return ResponseEntity.ok(movieService.findAllMovies());
    }
    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<String> addReviewToMovie(@PathVariable Long movieId, @RequestBody String reviewBody) {
        Movie movie = movieService.addReviewToMovie(movieId, reviewBody);

        return ResponseEntity.ok("Review added to movie with ID: " + movie.getId());
    }
}

