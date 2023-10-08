package security.example.security.service;

import security.example.security.model.Comment;

import java.util.List;


public interface CommentService {
    Comment saveComment(String body, String accessToken, Long newId);

    List<Comment> findListCommentByNewid(Long newId);
}
