package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.model.Cart;
import security.example.security.model.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);
    @Query("SELECT ur.roles FROM User ur WHERE ur.email = ?1")
    List<Role> findUserRolesByEmail(String userEmail);
}
