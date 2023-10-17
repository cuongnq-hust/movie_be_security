package security.example.security.service;

import security.example.security.model.Cart;
import security.example.security.model.CartItem;

import java.util.List;

public interface CartService {
    String addToCart(Long id, int quantity, String accessToken, Long idCart);

    void deleteCartItem(Long id, Long idCart, String accessToken);

    Cart getCartNow(String accessToken);

//    Cart createCart(String accessToken);
}
