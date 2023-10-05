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
    public Movie findMovieById(Long id) {
        System.out.println("id can tim la =======: "+ id);
        return movieRepository.findMovieById(id);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByMovieId(Long movieId) {
        System.out.println("id nhac duoc la" + movieId);
        return reviewRepository.findByMovieId(movieId);
    }

    @Transactional
    public Review addReviewToMovie(Long movieId, String reviewBody) {
        Review review = new Review();
        if (!movieRepository.existsById(movieId)) {
            throw new IllegalArgumentException("Invalid movie ID: " + movieId);
        }
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        review.setBody(reviewBody);
        review.setMovie(movie);
        movie.getReviews().add(review);
        movieRepository.save(movie);
        return reviewRepository.save(review);
    }
}
