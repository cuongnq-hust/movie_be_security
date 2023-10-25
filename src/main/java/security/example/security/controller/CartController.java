package security.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.CartDto;
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

    @GetMapping("/cartNow")
    public Cart getCartNow(@RequestHeader(name = "Authorization") String accessToken){
        return cartService.getCartNow(accessToken);
    }

    @PostMapping("/new")
    public Cart createItem(@RequestBody CartItemDto cartItemDto,
                               @RequestHeader(name = "Authorization") String accessToken) {
//        System.out.println("vao la" + cartItemDto);

        return cartService.addToCart(cartItemDto, accessToken);
    }
    @PostMapping("/deleteItem/{id}")
    public void deleteCartItem(@PathVariable Long id,
                               @RequestHeader(name = "Authorization") String accessToken
    ){
        cartService.deleteCartItem(id, accessToken);
    }

    @GetMapping("/findCartByid/{cartId}")
    public CartDto findCartById(@PathVariable Long cartId) {
        return cartService.getCartByIdCart(cartId);
    }

    @PostMapping("/createOrder")
    public Order createOrder(@RequestHeader(name = "Authorization") String accessToken){
        return orderService.createOrder(accessToken);
    }
    @PostMapping("/payOrder/{orderId}")
    public ResponseEntity<Void> payOrder(@PathVariable Long orderId) {
        orderService.checkOrder(orderId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/orderDetail/{orderId}")
    public OrderDto findOrderById(@PathVariable Long orderId) {
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/listOrder")
    public List<OrderDto> findMovieByTitle(@RequestHeader(name = "Authorization") String accessToken) {
        return orderService.findListOrderByUsername(accessToken);
    }

    @GetMapping("/listCartItem/{cartId}")
    private List<CartItemDto> findCartItemByCartId(@PathVariable Long cartId){
        return cartService.findCartItemByCartId(cartId);
    }
}
