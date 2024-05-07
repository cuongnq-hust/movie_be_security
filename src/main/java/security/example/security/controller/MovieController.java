package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.movie.MovieRequestDto;
import security.example.security.dto.movie.MovieSearchDto;
import security.example.security.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createMovie(@RequestBody MovieRequestDto movieRequestDto) throws Exception {
        movieService.saveMovie(movieRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/list")
    private ResponseEntity<List<MovieRequestDto>> getAllMovie(@RequestBody MovieSearchDto movieSearchDto) throws Exception {
        List<MovieRequestDto> movieRequestDtos = movieService.searchMovies(movieSearchDto);
        if (movieRequestDtos == null) {
            List<MovieRequestDto> movieRequestDtos1 = movieService.getAllMoview(movieSearchDto);
            movieService.saveMovies(movieSearchDto, movieRequestDtos1);
            return new ResponseEntity<>(movieRequestDtos1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(movieRequestDtos, HttpStatus.OK);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateMovie(@RequestBody MovieRequestDto movieRequestDto) throws Exception {
        movieService.updateMovie(movieRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieRequestDto> findMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<List<MovieRequestDto>> findMovieByTitle(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieByCategoryId(id));
    }

}
