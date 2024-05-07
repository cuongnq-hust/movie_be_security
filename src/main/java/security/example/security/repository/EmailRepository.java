package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.example.security.dto.email.EmailResponse;
import security.example.security.model.User;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<User, String> {
    @Query(value = """
            SELECT u.user_id AS email FROM USERS u 
            LEFT JOIN USER_ROLE ur ON u.user_id = ur.user_id
            WHERE ur.role_id = 1
            """, nativeQuery = true)
    List<EmailResponse> findListUser();
}
