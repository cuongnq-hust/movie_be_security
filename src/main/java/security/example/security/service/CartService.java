package security.example.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import security.example.security.dto.CartDto;
import security.example.security.dto.CartItemDto;
import security.example.security.entity.Movie;
import security.example.security.entity.Cart;
import security.example.security.entity.CartItem;
import security.example.security.repository.CartItemRepository;
import security.example.security.repository.CartRepository;
import security.example.security.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final JwtService jwtService;
    private final MovieRepository movieRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public CartService(JwtService jwtService, MovieRepository movieRepository, CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.jwtService = jwtService;
        this.movieRepository = movieRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    public CartDto getCartNow() {
        DecodedJWT jwt = jwtService.decodeToken();
        String userName = jwt.getSubject();
        List<Cart> cartList = cartRepository.findCartByUserName(userName);
        if (cartList.isEmpty()) {
            Cart cart = createNewCart(userName);
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            return cartDto;
        } else {
            CartDto cartDto = modelMapper.map(cartList.get(0), CartDto.class);
            return cartDto;
        }
    }

    public CartDto addToCart(CartItemDto cartItemDto) {
        CartDto cart = getCartNow();
        if (cart != null) {
            List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cart.getId());
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getMovie().getId().equals(cartItemDto.getMovieId())) {
                    if (cartItemDto.getQuantity() == 0) {
                        deleteCartItem(cartItemDto.getMovieId());
                        totalCart(cart.getId());
                        return cart;
                    } else {
                        cartItem.setQuantity(cartItemDto.getQuantity());
                        cartItemRepository.save(cartItem);
                        cart.setTotal(totalCart(cart.getId()));
                        return cart;
                    }
                }
            }
            Movie movie = movieRepository.findMovieById(cartItemDto.getMovieId());
            CartItem cartItem = new CartItem();
            Optional<Cart> cartOptional = cartRepository.findById(cart.getId());
            cartItem.setCart(cartOptional.get());
            cartItem.setMovie(movie);
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItemRepository.save(cartItem);
            cart.setTotal(totalCart(cart.getId()));
            return cart;
        } else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    public void deleteCartItem(Long id) {
        CartDto cart = getCartNow();
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cart.getId());
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getMovie().getId() == id) {
                cartItem.setDeleteFlag(1);
                cartItemRepository.save(cartItem);
            }
        }
        cart.setTotal(totalCart(cart.getId()));
    }


    public CartDto getCartByIdCart(Long idCart) {
        Cart cart = cartRepository.findCartById(idCart);
        return modelMapper.map(cart, CartDto.class);
    }

    private Cart createNewCart(String userName) {
        Cart cart = new Cart();
        cart.setCreatedBy(userName);
        return cartRepository.save(cart);
    }

    private float totalCart(Long idCart) {
        Cart cart = cartRepository.findCartById(idCart);
        float total = 0;
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(idCart);
        for (CartItem cartItem : cartItemList) {
            float total2 = cartItem.getQuantity() * cartItem.getMovie().getPrice();
            total += total2;
        }
        cart.setTotal(total);
        cartRepository.save(cart);
        return cart.getTotal();
    }

    public List<CartItemDto> findCartItemByCartId(Long cartId) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(cartId);
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            CartItemDto cartItemDtoOut = modelMapper.map(cartItem, CartItemDto.class);
            cartItemDtoList.add(cartItemDtoOut);
        }
        return cartItemDtoList;
    }
}
