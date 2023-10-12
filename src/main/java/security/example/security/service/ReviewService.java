package security.example.security.service;

import security.example.security.model.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(String body, Long id,String accessToken);

    List<Review> findReviewByMovieId(Long id);

    void deteleReview(Long id);

    Review updateReviewById(String body, Long id,String accessToken);
}
