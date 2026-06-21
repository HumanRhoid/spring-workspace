package com.example.domain.post.mapper;

import com.example.domain.post.dto.PostResponse;
import com.example.domain.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getMember().getId(),
                post.getMember().getName(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getCreatedAt()
        );
    }
}
