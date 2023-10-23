package security.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private boolean isPay = false;
    private Date create_At;
    private Date update_At;
    private String userName;
    private Long cartId;
}
