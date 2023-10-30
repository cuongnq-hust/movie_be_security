package security.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.example.security.dto.MovieDto;
import security.example.security.model.Movie;
import security.example.security.model.request.UploadFileRequest;
import security.example.security.service.impl.AwsS3ServiceImpl;
import security.example.security.service.impl.MovieServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieServiceImpl movieService;
    @Autowired
    private AwsS3ServiceImpl awsS3Service;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/new")
    public ResponseEntity<Movie> createMovie(
            @RequestBody MovieDto movieDto
    ) {
        return ResponseEntity.ok(movieService.saveMovie(movieDto));
    }

    @GetMapping("/all")
    private ResponseEntity<List<Movie>> getAllMovie() {
        return ResponseEntity.ok(movieService.getAllMoview());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }
    @GetMapping("/findByName/{title}")
    public ResponseEntity<List<Movie>> findMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(movieService.findMovieByName(title));
    }
    @GetMapping("/findByCategoryId/{id}")
    public ResponseEntity<List<Movie>> findMovieByTitle(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieByCategoryId(id));
    }

}
