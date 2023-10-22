package security.example.security.service.impl;

import org.springframework.stereotype.Service;
import security.example.security.dto.MovieDto;
import security.example.security.model.CategoryMovie;
import security.example.security.model.Movie;
import security.example.security.repository.CategoryRepository;
import security.example.security.repository.MovieRepository;
import security.example.security.service.MovieService;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Movie saveMovie(MovieDto movieDto) {
        CategoryMovie categoryMovie = categoryRepository.findCategoryMovieById(movieDto.getCategory_id());
        Movie movieNew = new Movie();
        movieNew.setTitle(movieDto.getTitle());
        movieNew.setTrailerLink(movieDto.getTrailerLink());
        movieNew.setPoster(movieDto.getPoster());
        movieNew.setAvatar(movieDto.getAvatar());
        movieNew.setPrice(movieDto.getPrice());
        movieNew.setCategoryMovie(categoryMovie);
        System.out.println("Them movie thang cong");
        return movieRepository.save(movieNew);
    }

    @Override
    public List<Movie> getAllMoview() {
        return movieRepository.findListMovies();
    }

    @Override
    public List<Movie> findMovieByName(String title) {
        List<Movie> movies = movieRepository.findMovieByTitle(title);
        return movies;
    }

    @Override
    public List<Movie> findMovieByCategoryId(Long id) {
        return movieRepository.findMovieByCategoryId(id);
    }

    @Override
    public Movie findMovieById(Long id) {
        return movieRepository.findMovieById(id);
    }
}
