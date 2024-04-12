package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.entity.Cart;
import security.example.security.entity.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartById(Long id);
    @Query(value = """
        SELECT * FROM CART ca WHERE ca.CREATED_BY = :user_name and ca.STATUS_PAY = 0
        """
            , nativeQuery = true)
    List<Cart> findCartByUserName(@Param("user_name") String user_name);

    @Query(value = """
        SELECT * FROM CART ca WHERE ca.CREATED_BY = :user_name and ca.STATUS_PAY = 0
        """, nativeQuery = true)
    Cart findCartByUserNameCart(@Param("user_name") String user_name);

    @Modifying
    @Query(value = """
        UPDATE CART ca SET ca.STATUS_PAY = 1 WHERE ca.id = :idCart
        """
            , nativeQuery = true)
    void updateCartToOrder(@Param("idCart") Long idCart);


    @Query(value = """
            SELECT * FROM cartitem cai WHERE cai.cart_id = :cartId
            """, nativeQuery = true)
    List<CartItem> findCartItemByCartId(@Param("cartId") Long cartId);
}

