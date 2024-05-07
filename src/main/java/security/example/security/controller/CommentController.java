package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.dto.comment.CommentResponseDto;
import security.example.security.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService comment;

    public CommentController(CommentService comment) {
        this.comment = comment;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createComment(@RequestBody String body, @PathVariable Long id) {
        comment.addComment(body, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findByReviewId/{id}")
    private ResponseEntity<List<CommentResponseDto>> getReviewByMovieId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(comment.findReviewByReviewId(id));
    }

    @DeleteMapping("delete/{id}")
    private void deleteCommentByid(@PathVariable Long id) {
        comment.deleteComment(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> updateCommentById(@RequestBody String body, @PathVariable Long id) {
        comment.updateCommentById(body, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
