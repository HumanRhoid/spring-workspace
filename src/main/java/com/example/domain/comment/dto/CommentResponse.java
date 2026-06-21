package com.example.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long postId,
        Long memberId,
        String memberName,
        String content,
        LocalDateTime createdAt
) {}
