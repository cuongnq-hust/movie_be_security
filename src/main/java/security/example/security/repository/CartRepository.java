package security.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import security.example.security.model.Cart;
import security.example.security.model.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartById(Long id);
    @Query(value = """
        select * from cart ca where ca.user_name = :user_name and ca.status_pay = 0
        """
            , countQuery = """
        select count(ca.id) from cart ca where ca.user_name = :user_name and ca.status_pay = 0
        """, nativeQuery = true)
    List<Cart> findCartByUserName(@Param("user_name") String user_name);

    @Query(value = """
        select * from cart ca where ca.user_name = :user_name and ca.status_pay = 0
        """
            , countQuery = """
        select count(ca.id) from cart ca where ca.user_name = :user_name and ca.status_pay = 0
        """, nativeQuery = true)
    Cart findCartByUserNameCart(@Param("user_name") String user_name);

    @Modifying
    @Query(value = """
        UPDATE Cart ca SET ca.status_pay = 1 WHERE ca.id = :idCart
        """
            , nativeQuery = true)
    void updateCartToOrder(@Param("idCart") Long idCart);


    @Query(value = """
            SELECT * FROM cartitem cai WHERE cai.cart_id = :cartId
            """, nativeQuery = true)
    List<CartItem> findCartItemByCartId(@Param("cartId") Long cartId);
}

