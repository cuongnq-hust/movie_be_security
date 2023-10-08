package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import security.example.security.model.NewDto;
import security.example.security.model.User;
import security.example.security.repository.NewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.JwtService;
import security.example.security.service.NewService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class NewServiceImpl implements NewService {
    private final JwtService jwtService;
    private final NewRepository newRepository;
    private final UserRepository userRepository;

    public NewServiceImpl(JwtService jwtService, NewRepository newRepository, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.newRepository = newRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NewDto saveNew(NewDto newDto, String accessToken) {
        String jwtToken = accessToken;
        String decodedToken = jwtToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        NewDto newDtoSimple = new NewDto();
        newDtoSimple.setTitle(newDto.getTitle());
        newDtoSimple.setImage(newDto.getImage());
        newDtoSimple.setUser(user);
        System.out.println("Them tin tuc thanh cong");
        newRepository.save(newDtoSimple);
        return newDtoSimple;
    }

    @Override
    public List<NewDto> findAllNew() {
        System.out.println("Liet ke danh sach la");
        return newRepository.findListNew();
    }

    @Override
    public NewDto getNewById(Long id) {
        System.out.println("Bai viet dc lay ra la biet viet so " + id);
        return newRepository.findNewDtoById(id);
    }
}
