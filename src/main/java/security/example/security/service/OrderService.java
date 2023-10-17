package security.example.security.service;

public interface OrderService {
    String createOrder(Long idCart, String accessToken);
}
