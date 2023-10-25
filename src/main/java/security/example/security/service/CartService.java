package security.example.security.service;

import security.example.security.dto.CartDto;
import security.example.security.dto.CartItemDto;
import security.example.security.model.Cart;

import java.util.List;


public interface CartService {
    CartDto addToCart(CartItemDto cartItemDto, String accessToken);

    CartDto deleteCartItem(Long id, String accessToken);

    CartDto getCartNow(String accessToken);

    CartDto getCartByIdCart(Long idCart);

    List<CartItemDto> findCartItemByCartId(Long cartId);
}
