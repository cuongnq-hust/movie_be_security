package security.example.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import security.example.security.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import com.auth0.jwt.JWTVerifier;
@Service
public class JwtService {
    @Value("123")
    private String secretKey;
    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities){
        //Phương thức này cung cấp mã hóa và tạo JWT cho người dùng và danh sách vai trò được cung cấp.
        // Nó sử dụng thuật toán HMAC256 và secretKey đã cấu hình để tạo chữ ký JWT.
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 50*60*1000))
                .withClaim("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        //Phương thức này tương tự với generateToken, tạo ra một JWT làm token làm mới người dùng để tái xác thực.
        // JWT này có thời gian hết hạn lâu hơn (70 phút) so với JWT làm token chính.
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 70*60*1000))
                .sign(algorithm);
    }
    public DecodedJWT decodeToken(String token, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
