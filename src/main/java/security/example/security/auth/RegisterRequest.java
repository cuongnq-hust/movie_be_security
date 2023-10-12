package security.example.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String user_name;
    private String email;
    private String password;
    private String mobile_number;
    private String image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlKtkny51Mx0H8vVChwctclFp_wcfvo_-Nmg&usqp=CAU";
}
