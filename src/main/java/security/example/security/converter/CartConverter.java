package security.example.security.converter;

import org.springframework.stereotype.Component;
import security.example.security.dto.CartDto;
import security.example.security.model.Cart;

@Component
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartConverter(CartItemConverter cartItemConverter) {
        this.cartItemConverter = cartItemConverter;
    }

    public CartDto toCartDto(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setCreate_At(cart.getCreate_At());
        cartDto.setUpdate_At(cart.getUpdate_At());
        cartDto.setCartItems(cartItemConverter.toDtoList(cart.getCartItems()));
        cartDto.setUser(cart.getUser());
        cartDto.setTotal(cart.getTotal());
        cartDto.setStatusPay(cart.isStatusPay());
        return cartDto;
    }

}
