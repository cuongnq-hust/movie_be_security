package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.model.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = """
        select * from cartitem ci where ci.cart_id = :id
        """
            , countQuery = """
        select count(ci.id) from cartitem ci where ci.cart_id = :id
        """, nativeQuery = true)
    List<CartItem> findCartItemByCartId(@Param("id") Long id);
}
