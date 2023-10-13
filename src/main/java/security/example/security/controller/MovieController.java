package security.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public Movie createMovie(
            @RequestBody String title,
            @RequestBody String trailerLink,
            @RequestPart("avatar") MultipartFile avatar,
            @RequestPart("poster") MultipartFile poster,
            @io.swagger.v3.oas.annotations.parameters.RequestBody UploadFileRequest request,
            @RequestBody Long category_id) {
        String avatarNew = null;
        String posterNew = null;
        try {
            avatarNew = awsS3Service.uploadFile(avatar, request);
            posterNew = awsS3Service.uploadFile(poster, request);
        } catch (Exception e) {
        }
        return movieService.saveMovie(title, trailerLink, posterNew, avatarNew, category_id);
    }

    @GetMapping("/all")
    private List<Movie> getAllMovie() {
        return movieService.getAllMoview();
    }

    @GetMapping("/{id}")
    public Movie findMovieById(@PathVariable Long id) {
        return movieService.findMovieById(id);
    }
    @GetMapping("/findByName")
    public List<Movie> findMovieByTitle(@RequestBody String title) {
        List<Movie> movies = movieService.findMovieByName(title);
        return movies;
    }
    @GetMapping("/findByCategoryId/{id}")
    public List<Movie> findMovieByTitle(@PathVariable Long id) {
        List<Movie> movies = movieService.findMovieByCategoryId(id);
        return movies;
    }
}
