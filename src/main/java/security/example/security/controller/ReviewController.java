package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Review;
import security.example.security.service.impl.MovieServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final MovieServiceImpl movieService;

    public ReviewController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<Review> addReviewToMovie(@PathVariable Long movieId, @RequestBody String reviewBody, @RequestHeader(name = "Authorization") String accessToken) {
//        System.out.println("id la" + movieId);
//        System.out.println("id la" + reviewBody);
//        System.out.println("id la" + accessToken);
        Review addedReview = movieService.addReviewToMovie(movieId, reviewBody, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }
    @GetMapping("/{movieId}/reviews")
    public List<Review> getReviewsByMovieId(@PathVariable Long movieId) {
        return movieService.getReviewsByMovieId(movieId);
    }
}
