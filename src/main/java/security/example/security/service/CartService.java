package security.example.security.service;

import security.example.security.dto.CartDto;
import security.example.security.dto.CartItemDto;
import security.example.security.model.Cart;
import security.example.security.model.CartItem;


public interface CartService {
    CartDto addToCart(CartItemDto cartItemDto, String accessToken);

    void deleteCartItem(Long id, String accessToken);

    Cart getCartNow(String accessToken);

    CartDto getCartByIdCart(Long idCart);

//    Cart createCart(String accessToken);
}
