package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.example.security.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
