package security.example.security.service;

import security.example.security.dto.CartItemDto;
import security.example.security.model.Cart;


public interface CartService {
    String addToCart(CartItemDto cartItemDto, String accessToken);

    void deleteCartItem(Long id, String accessToken);

    Cart getCartNow(String accessToken);

//    Cart createCart(String accessToken);
}
