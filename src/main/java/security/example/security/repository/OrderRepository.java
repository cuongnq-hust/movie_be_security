package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query(value = """
        UPDATE ORDERS ord SET ord.IS_PAY = 1 WHERE ord.ID = :idOrder
        """
            , nativeQuery = true)
    void checkOrder(@Param("idOrder") Long idOrder);



    @Query(value = """
        SELECT * FROM ORDERS ord WHERE ord.CREATED_BY = :user_name 
        """
          , nativeQuery = true)
    List<Order> findOrderByName(@Param("user_name") String user_name);

    Order findOrderById(Long id);
}
