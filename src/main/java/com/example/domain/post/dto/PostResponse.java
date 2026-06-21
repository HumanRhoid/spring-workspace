package com.example.domain.post.dto;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        Long memberId,
        String memberName,
        String title,
        String content,
        int viewCount,
        LocalDateTime createdAt
) {}
