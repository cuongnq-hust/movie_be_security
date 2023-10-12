package security.example.security.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.example.security.model.Comment;
import security.example.security.model.Review;
import security.example.security.model.User;
import security.example.security.repository.CommentRepository;
import security.example.security.repository.ReviewRepository;
import security.example.security.repository.UserRepository;
import security.example.security.service.CommentService;

import java.util.Date;
import java.util.List;

@Service
public class CommentImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public CommentImpl(CommentRepository commentRepository, ReviewRepository reviewRepository, UserRepository userRepository, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Comment addComment(String body, Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");

        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class);// Lấy danh sách vai trò từ JWT
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        Review review = reviewRepository.findReviewById(id);
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setUser(user);
        comment.setReview(review);
        System.out.println("Them comment thanh cong");
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findReviewByReviewId(Long id) {
        return commentRepository.findCommentsByReviewId(id);
    }

    @Override
    public void deleteComment(Long id) {
        System.out.println("Xoa comment thanh cong");
        commentRepository.deleteById(id);
    }

    @Override
    public Comment updateCommentById(String body, Long id, String accessToken) {
        String decodedToken = accessToken.replace("Bearer ", ""); // Loại bỏ tiền tố "Bearer " nếu có
        DecodedJWT jwt = jwtService.decodeToken(decodedToken, "123");

        String userName = jwt.getSubject(); // Lấy subject (email) từ JWT
        Date expiresAt = jwt.getExpiresAt(); // Lấy thời gian hết hạn của JWT
        List<String> roles = jwt.getClaim("roles").asList(String.class);// Lấy danh sách vai trò từ JWT
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        Comment comment = commentRepository.findCommentById(id);
        comment.setBody(body);
        System.out.println("Update comment thanh cong");
        return commentRepository.save(comment);
    }
    @Transactional
    @Override
    public void deleteCommentByReviewId(Long id) {
        commentRepository.deleteCommentsByReviewId(id);
    }
}
