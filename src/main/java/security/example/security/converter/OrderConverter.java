package security.example.security.converter;

import org.springframework.stereotype.Component;
import security.example.security.dto.OrderDto;
import security.example.security.model.Order;
@Component
public class OrderConverter {
    public OrderDto toOrderDTO(Order order) {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setId(order.getId());
        orderDTO.setPay(order.isPay());
        orderDTO.setUser(order.getUser());
        orderDTO.setCartId(order.getCart().getId());
        orderDTO.setCreate_At(order.getCreate_At());
        orderDTO.setUpdate_At(order.getUpdate_At());
        return orderDTO;
    }
}
