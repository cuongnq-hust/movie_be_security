package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.Comment;
import security.example.security.service.impl.CommentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/newComment/{id}")
    public ResponseEntity<Comment> addComment(@RequestBody String body  ,@RequestHeader(name = "Authorization") String accessToken,@PathVariable Long id){
        Comment comment = commentService.saveComment(body, accessToken, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
    @GetMapping("/list/{newId}")
    public List<Comment> getCommentList(@PathVariable Long newId){
//        System.out.println("list la" + newId);
        return commentService.findListCommentByNewid(newId);
    }
}
