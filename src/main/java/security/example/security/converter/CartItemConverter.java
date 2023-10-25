package security.example.security.converter;

import org.springframework.stereotype.Component;
import security.example.security.dto.CartItemDto;
import security.example.security.model.CartItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemConverter {
    public CartItemDto toDto(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartId(cartItem.getCart().getId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setMovieId(cartItem.getMovie().getId());
        cartItemDto.setPrice(cartItem.getMovie().getPrice());
        return cartItemDto;
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
