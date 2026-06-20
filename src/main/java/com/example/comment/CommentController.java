package com.example.comment;

import com.example.comment.dto.CommentRequest;
import com.example.comment.dto.CommentResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long postId,
            @RequestParam Long memberId,
            @RequestBody @Valid CommentRequest request) {
        log.info("POST /api/v1/posts/{}/comments - memberId={}", postId, memberId);
        return ResponseEntity.ok(commentService.create(postId, memberId, request));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> list(@PathVariable Long postId) {
        log.info("GET /api/v1/posts/{}/comments", postId);
        return ResponseEntity.ok(commentService.findByPost(postId));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequest request) {
        log.info("PUT /api/v1/comments/{}", id);
        return ResponseEntity.ok(commentService.update(id, request));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/comments/{}", id);
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
