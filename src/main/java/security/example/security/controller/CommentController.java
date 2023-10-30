package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Comment;
import security.example.security.service.impl.CommentImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentImpl comment;

    public CommentController(CommentImpl comment) {
        this.comment = comment;
    }
    @PostMapping("/{id}")
    public ResponseEntity<Comment> createComment(@RequestBody String body, @PathVariable Long id, @RequestHeader(name = "Authorization") String accessToken){
        Comment newcomment = comment.addComment(body,id,accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(newcomment);
    }
    @GetMapping("/findByReviewId/{id}")
    private ResponseEntity<List<Comment>> getReviewByMovieId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(comment.findReviewByReviewId(id));
    }
    @PostMapping("delete/{id}")
    private void deleteCommentByid(@PathVariable Long id){
        comment.deleteComment(id);
    }
    @PostMapping("update/{id}")
    private ResponseEntity<Comment> updateCommentById(@RequestBody String body,@PathVariable Long id,@RequestHeader(name = "Authorization") String accessToken){
        Comment update = comment.updateCommentById(body, id, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(update);
    }
    @PostMapping("/deleteByReviewId/{id}")
    public ResponseEntity<Void> deleteCommentsByReviewId(@PathVariable Long id) {
        comment.deleteCommentByReviewId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
