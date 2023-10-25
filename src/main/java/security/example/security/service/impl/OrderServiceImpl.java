package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.converter.OrderConverter;
import security.example.security.dto.OrderDto;
import security.example.security.model.Cart;
import security.example.security.model.Order;
import security.example.security.model.User;
import security.example.security.repository.*;
import security.example.security.service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public OrderServiceImpl(JwtService jwtService, UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderConverter orderConverter) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    @Transactional
    public Order createOrder(String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        Date currentTime = new Date();
        Cart cartNow = cartRepository.findCartByUserNameCart(userName);
        cartNow.setUpdate_At(currentTime);
        cartRepository.save(cartNow);
        cartRepository.updateCartToOrder(cartNow.getId());
        Cart cart = cartRepository.findCartById(cartNow.getId());
        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);
        System.out.println("tao order thanh cong");
        return orderRepository.save(order);
    }
    @Transactional
    @Override
    public void checkOrder(Long orderId) {
        Date currentTime = new Date();
        Order order = orderRepository.findOrderById(orderId);
        order.setUpdate_At(currentTime);
        orderRepository.save(order);
        orderRepository.checkOrder(orderId);
        System.out.println("tao check thanh cong");
    }

    @Override
    public OrderDto findOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        OrderDto orderDto = orderConverter.toOrderDTO(order);
        return orderDto;
    }

    @Override
    public List<OrderDto> findListOrderByUsername(String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", "");
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject();
        List<Order> orders = orderRepository.findOrderByName(userName);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders){
            OrderDto orderDto = orderConverter.toOrderDTO(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }


}
