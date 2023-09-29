package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.example.security.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
