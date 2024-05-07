package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.review.ReviewResponseDto;
import security.example.security.service.CommentService;
import security.example.security.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final CommentService comment;

    public ReviewController(ReviewService reviewService, CommentService comment) {
        this.reviewService = reviewService;
        this.comment = comment;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createReview(@RequestBody String body, @PathVariable Long id) {
        reviewService.saveReview(body, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findReviewByMovieId/{id}")
    private ResponseEntity<List<ReviewResponseDto>> getReviewByMovieId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewByMovieId(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteReviewById(@PathVariable Long id) {
//        comment.deleteCommentByReviewId(id);
        reviewService.deteleReview(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> updateReviewByid(@RequestBody String body, @PathVariable Long id) {
        reviewService.updateReviewById(body, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<ReviewResponseDto> getReviewByid(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewById(id));
    }
}
