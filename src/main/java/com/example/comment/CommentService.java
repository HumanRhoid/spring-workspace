package com.example.comment;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentRepository;
import com.example.comment.dto.CommentRequest;
import com.example.comment.dto.CommentResponse;
import com.example.member.domain.Member;
import com.example.member.domain.MemberRepository;
import com.example.post.domain.Post;
import com.example.post.domain.PostRepository;
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

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
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
        return CommentResponse.from(comment);
    }

    public List<CommentResponse> findByPost(Long postId) {
        log.debug("게시글 댓글 조회: postId={}", postId);
        return commentRepository.findActiveByPostId(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentResponse update(Long id, CommentRequest request) {
        log.info("댓글 수정: id={}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(request.content());
        return CommentResponse.from(comment);
    }

    @Transactional
    public void delete(Long id) {
        log.info("댓글 삭제(소프트): id={}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.delete();
    }
}
