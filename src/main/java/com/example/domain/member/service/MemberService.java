package com.example.domain.member.service;

import com.example.domain.member.dto.MemberRequest;
import com.example.domain.member.dto.MemberResponse;
import com.example.domain.member.entity.Member;
import com.example.domain.member.mapper.MemberMapper;
import com.example.domain.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Transactional
    public MemberResponse join(MemberRequest request) {
        log.info("회원 가입 요청: name={}, email={}", request.name(), request.email());
        validateDuplicateMember(request.name());
        Member member = memberMapper.toEntity(request);
        memberRepository.save(member);
        log.info("회원 가입 완료: id={}, name={}", member.getId(), member.getName());
        return memberMapper.toResponse(member);
    }

    public List<MemberResponse> findMembers() {
        log.debug("전체 회원 조회");
        return memberRepository.findAll().stream()
                .map(memberMapper::toResponse)
                .toList();
    }

    public MemberResponse findOne(Long id) {
        log.debug("회원 단건 조회: id={}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 회원 조회 시도: id={}", id);
                    return new IllegalArgumentException("존재하지 않는 회원입니다.");
                });
        return memberMapper.toResponse(member);
    }

    private void validateDuplicateMember(String name) {
        memberRepository.findByName(name).ifPresent(m -> {
            log.warn("중복 회원 가입 시도: name={}", name);
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
