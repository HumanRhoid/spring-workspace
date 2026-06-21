package com.example.domain.post.service;

import com.example.domain.member.entity.Member;
import com.example.domain.member.repository.MemberRepository;
import com.example.domain.post.dto.PostRequest;
import com.example.domain.post.dto.PostResponse;
import com.example.domain.post.entity.Post;
import com.example.domain.post.mapper.PostMapper;
import com.example.domain.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, MemberRepository memberRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postMapper = postMapper;
    }

    @Transactional
    public PostResponse create(Long memberId, PostRequest request) {
        log.info("게시글 작성: memberId={}, title={}", memberId, request.title());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Post post = new Post(member, request.title(), request.content());
        postRepository.save(post);
        log.info("게시글 작성 완료: postId={}", post.getId());
        return postMapper.toResponse(post);
    }

    public List<PostResponse> findAll() {
        log.debug("전체 게시글 조회");
        return postRepository.findAllActive().stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Transactional
    public PostResponse findOne(Long id) {
        log.debug("게시글 단건 조회: id={}", id);
        Post post = postRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 게시글 조회: id={}", id);
                    return new IllegalArgumentException("존재하지 않는 게시글입니다.");
                });
        post.increaseViewCount();
        return postMapper.toResponse(post);
    }

    @Transactional
    public PostResponse update(Long id, PostRequest request) {
        log.info("게시글 수정: id={}", id);
        Post post = postRepository.findActiveById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        post.update(request.title(), request.content());
        return postMapper.toResponse(post);
    }

    @Transactional
    public void delete(Long id) {
        log.info("게시글 삭제(소프트): id={}", id);
        Post post = postRepository.findActiveById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        post.delete();
    }
}
