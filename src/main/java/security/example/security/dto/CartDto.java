package security.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import security.example.security.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Date create_At;
    private Date update_At;
    private List<CartItemDto> cartItems = new ArrayList<>();
    private User user;
    private float total;
    private boolean statusPay;
}
   