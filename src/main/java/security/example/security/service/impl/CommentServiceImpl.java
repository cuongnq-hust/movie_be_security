package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import security.example.security.model.Comment;
import security.example.security.model.NewDto;
import security.example.security.model.User;
import security.example.security.repository.CommentRepository;
import security.example.security.repository.NewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.CommentService;
import security.example.security.service.JwtService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final JwtService jwtService;
    private final NewRepository newRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(JwtService jwtService, NewRepository newRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.jwtService = jwtService;
        this.newRepository = newRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment saveComment(String body, String accessToken, Long newId) {
        String jwtToken = accessToken;
        String decodedToken = jwtToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");
        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        System.out.println("User la" + userName);
        NewDto newDto = newRepository.findNewDtoById(newId);
        System.out.println("New Dto la"+ newId);
        Comment commentInput = new Comment();
        commentInput.setBody(body);
        commentInput.setUser(user);
        commentInput.setNewDto(newDto);
        System.out.println("Them comment thanh cong");
        commentRepository.save(commentInput);
        return null;
    }

    @Override
    public List<Comment> findListCommentByNewid(Long newId) {
//        System.out.println("list la");
        return commentRepository.findListCommentByNewId(newId);
    }
}
