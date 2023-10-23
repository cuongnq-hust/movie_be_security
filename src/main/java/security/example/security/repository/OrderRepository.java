package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.model.Cart;
import security.example.security.model.Order;
import security.example.security.model.Review;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query(value = """
        UPDATE orders ord SET ord.is_pay = 1 WHERE ord.id = :idOrder
        """
            , nativeQuery = true)
    void checkOrder(@Param("idOrder") Long idOrder);



    @Query(value = """
        select * from orders ord where ord.user_name = :user_name 
        """
            , countQuery = """
        select count(ord.id) from orders ord where ord.user_name = :user_name
        """, nativeQuery = true)
    List<Order> findOrderByName(@Param("user_name") String user_name);

    Order findOrderById(Long id);
}
