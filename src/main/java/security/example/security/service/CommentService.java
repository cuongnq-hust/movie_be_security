package security.example.security.service;

import security.example.security.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(String body, Long id, String accessToken);
    List<Comment> findReviewByReviewId(Long id);

    void deleteComment(Long id);

    Comment updateCommentById(String body, Long id,String accessToken);

    void  deleteCommentByReviewId(Long id);
}

