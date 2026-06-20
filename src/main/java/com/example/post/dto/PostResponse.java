package com.example.post.dto;

import com.example.post.domain.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        Long memberId,
        String memberName,
        String title,
        String content,
        int viewCount,
        LocalDateTime createdAt
) {
    public static PostResponse from(Post post) {
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
