package security.example.security.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import security.example.security.entity.CategoryEntity;
import security.example.security.entity.Movie;
import security.example.security.dto.movie.MovieRequestDto;
import security.example.security.dto.movie.MovieSearchDto;
import security.example.security.repository.CategoryRepository;
import security.example.security.repository.MovieRepository;
import security.example.security.service.RedisService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final RedisService<MovieRequestDto> redisService;

    public MovieService(MovieRepository movieRepository, CategoryRepository categoryRepository, RedisService<MovieRequestDto> redisService) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.redisService = redisService;
    }

    public void saveMovie(MovieRequestDto movieRequestDto) throws Exception {
        CategoryEntity categoryMovie = categoryRepository.findCategoryMovieById(movieRequestDto.getCategoryId());
        Movie movie = modelMapper.map(movieRequestDto, Movie.class);
        movie.setCategoryMovie(categoryMovie);
        movieRepository.save(movie);
        redisService.deleteKeysStartingWith("movie");
    }

    public List<MovieRequestDto> getAllMoview(MovieSearchDto movieSearchDto) {
        List<Movie> movieList = movieRepository.findListMovies(movieSearchDto);
        return movieList.stream().map(item -> modelMapper.map(item, MovieRequestDto.class))
                .collect(Collectors.toList());
    }

    public void updateMovie(MovieRequestDto movieRequestDto) throws Exception {
        CategoryEntity categoryMovie = categoryRepository.findCategoryMovieById(movieRequestDto.getCategoryId());
        Optional<Movie> optionalMovie = movieRepository.findById(movieRequestDto.getId());
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setCategoryMovie(categoryMovie);
            movie.setTitle(movieRequestDto.getTitle());
            movie.setTrailerLink(movieRequestDto.getTrailerLink());
            movie.setPoster(movieRequestDto.getPoster());
            movie.setAvatar(movieRequestDto.getAvatar());
            movie.setPrice(movieRequestDto.getPrice());
            movieRepository.save(movie);
            redisService.deleteKeysStartingWith("movie");
        }
    }

    public MovieRequestDto findMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return modelMapper.map(movie.get(), MovieRequestDto.class);
        }
        return null;
    }

    public List<MovieRequestDto> findMovieByCategoryId(Long id) {
        List<Movie> movieList = movieRepository.findMovieByCategoryId(id);
        return movieList.stream().map(item -> modelMapper.map(item, MovieRequestDto.class))
                .collect(Collectors.toList());
    }

    private String getKeyFrom(MovieSearchDto movieSearchDto) {
        StringBuilder keyBuilder = new StringBuilder("movie:");
        if (movieSearchDto.getTitle() != null) {
            keyBuilder.append(movieSearchDto.getTitle()).append(":");
        }
        return keyBuilder.toString();
    }

    public List<MovieRequestDto> searchMovies(MovieSearchDto movieSearchDto) throws Exception {
        String key = getKeyFrom(movieSearchDto);
        return redisService.searchList(key);
    }

    public void saveMovies(MovieSearchDto movieSearchDto, List<MovieRequestDto> movies) throws Exception {
        String key = getKeyFrom(movieSearchDto);
        redisService.saveList(key, movies);
    }
}
