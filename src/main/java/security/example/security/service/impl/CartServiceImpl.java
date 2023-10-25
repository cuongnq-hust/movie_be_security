package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import security.example.security.converter.CartConverter;
import security.example.security.converter.CartItemConverter;
import security.example.security.dto.CartDto;
import security.example.security.dto.CartItemDto;
import security.example.security.model.Movie;
import security.example.security.model.User;
import security.example.security.model.Cart;
import security.example.security.model.CartItem;
import security.example.security.repository.CartItemRepository;
import security.example.security.repository.CartRepository;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.CartService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;
    private final CartItemConverter cartItemConverter;

    public CartServiceImpl(JwtService jwtService, UserRepository userRepository, MovieRepository movieRepository, CartItemRepository cartItemRepository, CartRepository cartRepository, CartConverter cartConverter, CartItemConverter cartItemConverter) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.cartConverter = cartConverter;
        this.cartItemConverter = cartItemConverter;
    }

    @Override
    public Cart getCartNow(String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));

        List<Cart> cartList = cartRepository.findCartByUserName(userName);
        if (cartList.isEmpty()) {
            Cart cart = createNewCart(user);
            System.out.println("Đã tạo giỏ hàng mới");
            return cart;
        } else {
            return cartList.get(0);
        }
    }

    @Override
    public Cart addToCart(CartItemDto cartItemDto, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Cart cart = cartRepository.findCartByUserNameCart(userName);

        if (cart != null){
            List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cart.getId());

            for (CartItem cartItem: cartItemList){
                if (cartItem.getMovie().getId().equals(cartItemDto.getMovieId())){
                    cartItem.setQuantity(cartItemDto.getQuantity());
                    cartItemRepository.save(cartItem);
                    return cart;
                }
            }
            Movie movie = movieRepository.findMovieById(cartItemDto.getMovieId());
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMovie(movie);
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItemRepository.save(cartItem);
            return cart;
        }else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    @Override
    public void deleteCartItem(Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Cart cart = cartRepository.findCartByUserNameCart(userName);
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cart.getId());
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getMovie().getId() == id) {
                cartItemRepository.delete(cartItem);
                System.out.println("Xoa thanh cong");
            }
        }
    }



    @Override
    public CartDto getCartByIdCart(Long idCart) {
        Cart cart = cartRepository.findCartById(idCart);
        CartDto cartDto = cartConverter.toCartDto(cart);
        return cartDto;
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    @Override
    public List<CartItemDto> findCartItemByCartId(Long cartId) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cartId);
        List<CartItemDto> cartItemDtoOutList = new ArrayList<>();
        for (CartItem cartItem: cartItemList){
            CartItemDto cartItemDtoOut = cartItemConverter.toDto(cartItem);
            cartItemDtoOutList.add(cartItemDtoOut);
        }
        return cartItemDtoOutList;
    }
}
