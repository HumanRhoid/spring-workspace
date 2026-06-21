package com.example.domain.comment.mapper;

import com.example.domain.comment.dto.CommentResponse;
import com.example.domain.comment.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment) {
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
