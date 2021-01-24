package pl.sda.blogservicedata.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.sda.blogservicedata.model.Comment;
import pl.sda.blogservicedata.model.request.CommentDto;
import pl.sda.blogservicedata.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Secured({"ROLE_AUTHOR", "ROLE_READER"})
    @PostMapping(path = "/{blogPostId}/comments")
    public ResponseEntity<Comment> addCommentToBlogPost(@PathVariable Long blogPostId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addCommentToBlogPost(blogPostId, commentDto));
    }
}
