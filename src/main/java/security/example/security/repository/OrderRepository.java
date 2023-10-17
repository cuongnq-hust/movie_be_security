package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.example.security.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
