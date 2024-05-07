package security.example.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import security.example.security.entity.CommentEntity;
import security.example.security.entity.Review;
import security.example.security.dto.comment.CommentResponseDto;
import security.example.security.repository.CommentRepository;
import security.example.security.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.jwtService = jwtService;
    }

    public void addComment(String body, Long id) {
        DecodedJWT jwt = jwtService.decodeToken();
        String userName = jwt.getSubject();
        Review review = reviewRepository.findReviewById(id);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBody(body);
        commentEntity.setCreatedBy(userName);
        commentEntity.setReview(review);
        commentRepository.save(commentEntity);
    }

    public List<CommentResponseDto> findReviewByReviewId(Long id) {
        List<CommentEntity> commentEntityList = commentRepository.findCommentsByReviewId(id);
        return commentEntityList.stream().map(item -> modelMapper.map(item, CommentResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            CommentEntity comment = optional.get();
            comment.setDeleteFlag(1);
            commentRepository.save(comment);
        }
    }

    public void updateCommentById(String body, Long id) {
        CommentEntity commentEntity = commentRepository.findCommentById(id);
        commentEntity.setBody(body);
        commentRepository.save(commentEntity);
    }

}
