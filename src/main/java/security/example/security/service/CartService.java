package security.example.security.service;

import security.example.security.dto.CartDto;
import security.example.security.dto.CartItemDto;
import security.example.security.model.Cart;

import java.util.List;


public interface CartService {
    Cart addToCart(CartItemDto cartItemDto, String accessToken);

    void deleteCartItem(Long id, String accessToken);

    Cart getCartNow(String accessToken);

    CartDto getCartByIdCart(Long idCart);

    List<CartItemDto> findCartItemByCartId(Long cartId);
}
