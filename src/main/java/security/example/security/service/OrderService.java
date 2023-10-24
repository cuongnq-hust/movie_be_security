package security.example.security.service;

import security.example.security.dto.OrderDto;
import security.example.security.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String accessToken);

    void checkOrder(Long orderId);

    OrderDto findOrderById(Long orderId);

    List<OrderDto> findListOrderByUsername(String accessToken);
}
