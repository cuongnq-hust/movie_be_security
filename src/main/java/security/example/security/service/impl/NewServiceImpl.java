package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import security.example.security.model.NewDto;
import security.example.security.model.User;
import security.example.security.repository.NewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.JwtService;
import security.example.security.service.NewService;

import java.io.IOException;
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
    public NewDto saveNew(NewDto newDto, String accessToken, MultipartFile file) throws IOException {
        String jwtToken = accessToken;
        String decodedToken = jwtToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class);
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        NewDto newDtoSimple = new NewDto();
        newDtoSimple.setTitle(newDto.getTitle());
        newDtoSimple.setImage(file.getBytes());
        newDtoSimple.setUser(user);

        System.out.println("Thêm tin tức thành công");

        newRepository.save(newDtoSimple);
        return newDtoSimple;
    }

    @Override
    public List<NewDto> findAllNew() {
        return newRepository.findListNew();
    }

    @Override
    public NewDto getNewById(Long id) {
        return newRepository.findNewDtoById(id);
    }

//    @Override
//    public NewDto updateNew(Long id, String title, String image, String accessToken) {
//        String decodedToken = accessToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
//        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
//        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
//        NewDto newDto = newRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid new ID: " + id));
//        newDto.setTitle(title);
//        newDto.setImage(image);
//        System.out.println("Sửa bài viết thành công");
//        return newRepository.save(newDto);
//    }

    @Override
    public void deleteNew(Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        newRepository.deleteById(id);
        System.out.println("Xóa bài viết thành công");
    }
}
