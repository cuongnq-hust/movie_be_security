package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import security.example.security.model.Movie;
import security.example.security.model.User;
import security.example.security.model.Cart;
import security.example.security.model.CartItem;
import security.example.security.repository.CartItemRepository;
import security.example.security.repository.CartRepository;
import security.example.security.repository.MovieRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.CartService;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(JwtService jwtService, UserRepository userRepository, MovieRepository movieRepository, CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }
//    @Override
//    public Cart createCart(String accessToken){
//        String decodedToken = accessToken.replace("Bearer ", "");
//        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
//        String userName = jwt.getSubject();
//        Date expiresAt = jwt.getExpiresAt();
//        List<String> roles = jwt.getClaim("roles").asList(String.class);
//        User user = userRepository.findByEmail(userName)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
//        Cart cart = new Cart();
//        cart.setUser(user);
//        System.out.println("tao cart thanh cong");
//        return cartRepository.save(cart);
//    }

    @Override
    public String addToCart(Long id, int quantity, String accessToken, Long idCart) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Date expiresAt = jwt.getExpiresAt();
        List<String> roles = jwt.getClaim("roles").asList(String.class);

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));

        Cart cart = cartRepository.findCartById(idCart);

        if (cart != null){
            List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(idCart);

            for (CartItem cartItem: cartItemList){
                if (cartItem.getMovie().getId().equals(id)){
                    cartItem.setQuantity(quantity);
                    cartItemRepository.save(cartItem);
                    return "";
                }
            }

            Movie movie = movieRepository.findMovieById(id);
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMovie(movie);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            return "";
        }else {
            return "";
        }
    }

    @Override
    public void deleteCartItem(Long id, Long idCart, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Date expiresAt = jwt.getExpiresAt();
        List<String> roles = jwt.getClaim("roles").asList(String.class);

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));

        Cart cart = cartRepository.findCartById(idCart);
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCartId(idCart);
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getMovie().getId() == id) {
                cartItemRepository.delete(cartItem);
                System.out.println("Xoa thanh cong");
            }
        }
    }

    @Override
    public Cart getCartNow(String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Date expiresAt = jwt.getExpiresAt();
        List<String> roles = jwt.getClaim("roles").asList(String.class);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        List<Cart> cartList = cartRepository.findCartByUserName(userName);
        if (cartList.size() == 0) {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        } else {
            return cartList.get(0);
        }
    }
}
