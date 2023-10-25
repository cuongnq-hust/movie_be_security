package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import security.example.security.model.Movie;
import security.example.security.model.Review;
import security.example.security.model.User;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.ReviewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JwtService jwtService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository, JwtService jwtService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Review saveReview(String body, Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");

        String userName = jwt.getSubject();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        Movie movie = movieRepository.findMovieById(id);
        Review review = new Review();
        review.setBody(body);
        review.setMovie(movie);
        review.setUser(user);
        System.out.println("Tao review thanh cong");
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findReviewByMovieId(Long id) {
        return reviewRepository.findReviewByMovieId(id);
    }

    @Override
    public void deteleReview(Long id) {
        System.out.println("Xoa review thanh cong");
        reviewRepository.deleteById(id);
    }

    @Override
    public Review updateReviewById(String body, Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");

        String userName = jwt.getSubject();
        Review review = reviewRepository.findReviewById(id);
        review.setBody(body);
        System.out.println("Update Thanh Cong");
        return reviewRepository.save(review);
    }

    @Override
    public Review findReviewById(Long id) {
        return reviewRepository.findReviewById(id);
    }
}
