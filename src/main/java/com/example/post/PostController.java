package com.example.post;

import com.example.post.dto.PostRequest;
import com.example.post.dto.PostResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(
            @RequestParam Long memberId,
            @RequestBody @Valid PostRequest request) {
        log.info("POST /api/v1/posts - memberId={}", memberId);
        return ResponseEntity.ok(postService.create(memberId, request));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> list() {
        log.info("GET /api/v1/posts");
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findOne(@PathVariable Long id) {
        log.info("GET /api/v1/posts/{}", id);
        return ResponseEntity.ok(postService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid PostRequest request) {
        log.info("PUT /api/v1/posts/{}", id);
        return ResponseEntity.ok(postService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/posts/{}", id);
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
