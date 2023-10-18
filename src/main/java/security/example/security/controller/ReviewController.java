package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Review;
import security.example.security.service.impl.CommentImpl;
import security.example.security.service.impl.ReviewServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final CommentImpl comment;

    public ReviewController(ReviewServiceImpl reviewService, CommentImpl comment) {
        this.reviewService = reviewService;
        this.comment = comment;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Review> createReview(@RequestBody String body, @PathVariable Long id, @RequestHeader(name = "Authorization") String accessToken){
        Review addedReview = reviewService.saveReview(body, id, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }
    @GetMapping("/findReviewByMovieId/{id}")
    private List<Review> getReviewByMovieId(@PathVariable Long id){
        return reviewService.findReviewByMovieId(id);
    }
    @PostMapping("delete/{id}")
    private void deleteReviewById(@PathVariable Long id){
        comment.deleteCommentByReviewId(id);
        reviewService.deteleReview(id);
    }
    @PostMapping("update/{id}")
    private ResponseEntity<Review> updateReviewByid(@RequestBody String body,@PathVariable Long id,@RequestHeader(name = "Authorization") String accessToken){
        Review update = reviewService.updateReviewById(body, id, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(update);
    }
    @GetMapping("review/{id}")
    private ResponseEntity<Review> getReviewByid(@PathVariable Long id){
        Review review = reviewService.findReviewById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }
}
