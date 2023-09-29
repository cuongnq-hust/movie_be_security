package security.example.security.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.model.Movie;
import security.example.security.model.Review;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.ReviewRepository;
import security.example.security.service.MovieService;

import java.util.List;


@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
//        System.out.println("movie vao la:" + movie.toString());
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
    @Transactional
    public Movie addReviewToMovie(Long movieId, String reviewBody) {
        System.out.println("id la" + movieId);
        System.out.println("body la" + reviewBody);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));

        Review review = new Review();
        review.setBody(reviewBody);
//        review.setMovie(movie);
        movie.getReviews().add(review);
        return movieRepository.save(movie);
    }
}
