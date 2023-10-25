package security.example.security.converter;

import org.springframework.stereotype.Component;
import security.example.security.dto.CartItemDto;
import security.example.security.model.CartItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemConverter {
    public CartItemDto toDto(CartItem cartItem){
        CartItemDto CartItemDto = new CartItemDto();
        CartItemDto.setCartId(cartItem.getCart().getId());
        CartItemDto.setQuantity(cartItem.getQuantity());
        CartItemDto.setMovieId(cartItem.getMovie().getId());
        return CartItemDto;
    }

    public List<CartItemDto> toDtoList(List<CartItem> cartItemList){
        List<CartItemDto> dtoList = new ArrayList<>();
        for (CartItem cartItem: cartItemList){
            CartItemDto cartItemDto = toDto(cartItem);
            dtoList.add(cartItemDto);
        }
        return dtoList;
    }
}
