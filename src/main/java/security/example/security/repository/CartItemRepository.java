package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.entity.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = """
        SELECT * FROM CART_ITEM ci WHERE ci.CART_ID = :id AND
        ci.DELETE_FLAG = 0
        """, nativeQuery = true)
    List<CartItem> findCartItemByCartId(@Param("id") Long id);
}
