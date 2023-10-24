package security.example.security.converter;

import org.springframework.stereotype.Component;
import security.example.security.dto.MovieDto;
import security.example.security.model.Movie;
@Component
public class MovieConverter {
    public MovieDto toDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setTrailerLink(movie.getTrailerLink());
        movieDto.setPoster(movie.getPoster());
        movieDto.setAvatar(movie.getAvatar());
        movieDto.setPrice(movie.getPrice());
        movieDto.setCategory_id(movie.getCategoryMovie().getId());
        // Thêm các thông tin khác vào movieDto nếu cần thiết
        return movieDto;
    }

    public Movie toEntity(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        // Thêm các thông tin khác vào movie nếu cần thiết
        return movie;
    }
}
