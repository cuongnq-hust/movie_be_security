package security.example.security.service;

import security.example.security.model.Cart;


public interface CartService {
    String addToCart(Long id, int quantity, String accessToken);

    void deleteCartItem(Long id, String accessToken);

    Cart getCartNow(String accessToken);

//    Cart createCart(String accessToken);
}
