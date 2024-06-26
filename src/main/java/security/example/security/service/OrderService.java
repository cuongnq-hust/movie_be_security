package security.example.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.dto.OrderDto;
import security.example.security.entity.Cart;
import security.example.security.entity.Order;
import security.example.security.repository.CartRepository;
import security.example.security.repository.OrderRepository;
import security.example.security.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final JwtService jwtService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public OrderService(JwtService jwtService, CartRepository cartRepository, OrderRepository orderRepository) {
        this.jwtService = jwtService;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDto createOrder() {
        DecodedJWT jwt = jwtService.decodeToken();
        String userName = jwt.getSubject();

        Cart cartNow = cartRepository.findCartByUserNameCart(userName);
        cartRepository.updateCartToOrder(cartNow.getId());
        Order order = new Order();
        order.setCreatedBy(userName);
        order.setCart(cartNow);
        order.setTotal(cartNow.getTotal());
        order.setTax(cartNow.getTotal() / 10);
        order.setFee(cartNow.getTotal() / 20);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDto.class);
    }

    @Transactional
    public void checkOrder(Long orderId) {
        orderRepository.checkOrder(orderId);
    }

    public OrderDto findOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return modelMapper.map(order, OrderDto.class);
    }

    public List<OrderDto> findListOrderByUsername() {
        DecodedJWT jwt = jwtService.decodeToken();
        String userName = jwt.getSubject();
        List<Order> orders = orderRepository.findOrderByName(userName);
        return orders.stream().map(item -> modelMapper.map(item, OrderDto.class))
                .collect(Collectors.toList());
    }
}
