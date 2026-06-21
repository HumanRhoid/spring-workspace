package com.example.domain.comment.service;

import com.example.domain.comment.dto.CommentRequest;
import com.example.domain.comment.dto.CommentResponse;
import com.example.domain.comment.entity.Comment;
import com.example.domain.comment.mapper.CommentMapper;
import com.example.domain.comment.repository.CommentRepository;
import com.example.domain.member.entity.Member;
import com.example.domain.member.repository.MemberRepository;
import com.example.domain.post.entity.Post;
import com.example.domain.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          MemberRepository memberRepository,
                          CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional
    public CommentResponse create(Long postId, Long memberId, CommentRequest request) {
        log.info("댓글 작성: postId={}, memberId={}", postId, memberId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Comment comment = new Comment(post, member, request.content());
        commentRepository.save(comment);
        log.info("댓글 작성 완료: commentId={}", comment.getId());
        return commentMapper.toResponse(comment);
    }

    public List<CommentResponse> findByPost(Long postId) {
        log.debug("게시글 댓글 조회: postId={}", postId);
        return commentRepository.findActiveByPostId(postId).stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse update(Long id, CommentRequest request) {
        log.info("댓글 수정: id={}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(request.content());
        return commentMapper.toResponse(comment);
    }

    @Transactional
    public void delete(Long id) {
        log.info("댓글 삭제(소프트): id={}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.delete();
    }
}
