package com.example.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.deletedAt IS NULL ORDER BY c.createdAt ASC")
    List<Comment> findActiveByPostId(Long postId);
}
