package security.example.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import security.example.security.entity.Movie;
import security.example.security.entity.Review;
import security.example.security.dto.review.ReviewResponseDto;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.ReviewRepository;
import security.example.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JwtService jwtService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository, JwtService jwtService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.jwtService = jwtService;
    }

    public void saveReview(String body, Long id) {
        DecodedJWT jwt = jwtService.decodeToken();

        String userName = jwt.getSubject();
        Movie movie = movieRepository.findMovieById(id);
        Review review = new Review();
        review.setBody(body);
        review.setMovie(movie);
        review.setCreatedBy(userName);
        reviewRepository.save(review);
    }

    public List<ReviewResponseDto> findReviewByMovieId(Long id) {
        List<Review> reviewList = reviewRepository.findReviewByMovieId(id);
        return reviewList.stream().map(item -> modelMapper.map(item, ReviewResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deteleReview(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setDeleteFlag(1);
            reviewRepository.save(review);
        }
    }

    public void updateReviewById(String body, Long id) {
        Review review = reviewRepository.findReviewById(id);
        review.setBody(body);
        reviewRepository.save(review);
    }

    public ReviewResponseDto findReviewById(Long id) {
        Review review = reviewRepository.findReviewById(id);

        return modelMapper.map(review, ReviewResponseDto.class);
    }
}
