package com.example.comment.dto;

import com.example.comment.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long postId,
        Long memberId,
        String memberName,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getMember().getId(),
                comment.getMember().getName(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
