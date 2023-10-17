package security.example.security.controller;

import org.springframework.web.bind.annotation.*;
import security.example.security.model.Cart;
import security.example.security.model.CartItem;
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
    public String createCart(@RequestParam Long id,
                               @RequestParam int quantity,
                               @RequestParam Long idCart,
                               @RequestHeader(name = "Authorization") String accessToken) {
        return cartService.addToCart(id, quantity, accessToken, idCart);
    }
    @PostMapping("/deleteItem")
    public void deleteCartItem(@RequestParam Long id,
                               @RequestParam Long idCart,
                               @RequestHeader(name = "Authorization") String accessToken
                               ){
        cartService.deleteCartItem(id, idCart, accessToken);
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
    public String createOrder(@RequestParam Long idCart, @RequestHeader(name = "Authorization") String accessToken){
        return orderService.createOrder(idCart, accessToken);
    }
}
