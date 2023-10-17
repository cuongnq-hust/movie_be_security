package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.model.Cart;
import security.example.security.model.Order;
import security.example.security.model.User;
import security.example.security.repository.*;
import security.example.security.service.OrderService;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(JwtService jwtService, UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public String createOrder(Long idCart, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        Date expiresAt = jwt.getExpiresAt();
        List<String> roles = jwt.getClaim("roles").asList(String.class);

        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        Cart cartNow = cartRepository.findCartById(idCart);
        if (cartNow.getUser().getEmail().equals(userName)){
            cartRepository.updateCartToOrder(idCart);
            Cart cart = cartRepository.findCartById(idCart);
            Order order = new Order();
            order.setUser(user);
            order.setCart(cart);
            orderRepository.save(order);
            System.out.println("tao order thanh cong");
        }else {
            System.out.println("Ban Khong Phai Chu So Huu");
        }
        return null;
    }
}
