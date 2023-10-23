package security.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.CartItemDto;
import security.example.security.dto.OrderDto;
import security.example.security.model.Cart;
import security.example.security.model.Order;
import security.example.security.service.impl.CartServiceImpl;
import security.example.security.service.impl.OrderServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;

    public CartController(CartServiceImpl cartService, OrderServiceImpl orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostMapping("/new")
    public Cart createItem(@RequestBody CartItemDto cartItemDto,
                               @RequestHeader(name = "Authorization") String accessToken) {
//        System.out.println("vao la" + cartItemDto);
        return cartService.addToCart(cartItemDto, accessToken);
    }
    @PostMapping("/deleteItem")
    public void deleteCartItem(@RequestBody Long id,
                               @RequestHeader(name = "Authorization") String accessToken
    ){
        cartService.deleteCartItem(id, accessToken);
    }
    @GetMapping("/cartNow")
    public Cart getCartNow(@RequestHeader(name = "Authorization") String accessToken){
        return cartService.getCartNow(accessToken);
    }
//    @PostMapping("/create")
//    public Cart createCart(@RequestHeader(name = "Authorization")String accessToken){
//        return cartService.createCart(accessToken);
//    }

    @PostMapping("/updateOrder")
    public Order createOrder(@RequestHeader(name = "Authorization") String accessToken){
        return orderService.createOrder(accessToken);
    }
    @PostMapping("/payOrder/{orderId}")
    public ResponseEntity<Void> payOrder(@PathVariable Long orderId) {
        orderService.checkOrder(orderId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/listOrder/{orderId}")
    public OrderDto findOrderById(@PathVariable Long orderId) {
        return orderService.findOrderById(orderId);
    }
}
