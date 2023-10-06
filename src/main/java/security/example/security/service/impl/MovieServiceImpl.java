package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.model.Movie;
import security.example.security.model.Review;
import security.example.security.model.User;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.ReviewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.JwtService;
import security.example.security.service.MovieService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;


@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;

    public MovieServiceImpl(UserRepository userRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Movie createMovie(Movie movie) {
//        System.out.println("movie vao la:" + movie.toString());
        return movieRepository.save(movie);
    }

    @Override
    public Movie findMovieById(Long id) {
        System.out.println("id can tim la =======: " + id);
        return movieRepository.findMovieById(id);
    }

    @Override
    public List<Movie> findAllMovies() throws JsonProcessingException {
//        return movieRepository.findAll();

        LOGGER.info("getAllEmployee fail: " + objectMapper.writeValueAsString(movieRepository.findListMovies()));


        return movieRepository.findListMovies();
    }

    @Override
    public List<Review> getReviewsByMovieId(Long movieId) {
        System.out.println("id nhac duoc la" + movieId);
        return reviewRepository.findByMovieId(movieId);
    }

    @Transactional
    public Review addReviewToMovie(Long movieId, String reviewBody, String accessToken) {
        System.out.println("nhan dc la" + movieId);
        System.out.println("nhan dc la" + reviewBody);
        System.out.println("nhan la" + accessToken);
        // Giải mã access_token và lấy thông tin người dùng từ đó
        String jwtToken = accessToken;
        String decodedToken = jwtToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");

        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class); // Lấy danh sách vai trò từ JWT
        System.out.println("Subject: " + userName);
        System.out.println("Expires At: " + expiresAt);
        System.out.println("Roles: " + roles);


//         Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu hay không
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        System.out.println("User la` : " + user);
// Kiểm tra xem phim có tồn tại trong cơ sở dữ liệu hay không
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        System.out.println("Movie la` : " + movie.getTitle());
        // Tạo đánh giá mới
        Review review = new Review();
        review.setBody(reviewBody);

        review.setMovie(movie);
        movie.getReviews().add(review);

        review.setUser(user);
        user.getReviews().add(review);
        movieRepository.save(movie);
        userRepository.save(user);
        reviewRepository.save(review);
        return review;
    }
}

