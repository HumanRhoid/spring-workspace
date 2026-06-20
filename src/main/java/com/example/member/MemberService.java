package com.example.member;

// ===== 기존 코드 =====
// @Service
// public class MemberService {
//     private final MemberRepository memberRepository;
//
//     public MemberService(MemberRepository memberRepository) {
//         this.memberRepository = memberRepository;
//     }
//
//     public Long join(Member member) {
//         validateDuplicateMember(member);
//         memberRepository.save(member);
//         return member.getId();
//     }
//
//     private void validateDuplicateMember(Member member) {
//         memberRepository.findByName(member.getName()).ifPresent(m -> {
//             throw new IllegalStateException("이미 존재하는 회원입니다.");
//         });
//     }
//
//     public List<Member> findMembers() {
//         return memberRepository.findAll();
//     }
//
//     public Optional<Member> findOne(Long memberId) {
//         return memberRepository.findByID(memberId);
//     }
// }

// ===== 최신 구조 =====
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

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse join(MemberRequest request) {
        log.info("회원 가입 요청: name={}, email={}", request.name(), request.email());
        validateDuplicateMember(request.name());
        Member member = new Member(request.name(), request.email(), request.password());
        memberRepository.save(member);
        log.info("회원 가입 완료: id={}, name={}", member.getId(), member.getName());
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findMembers() {
        log.debug("전체 회원 조회");
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findOne(Long id) {
        log.debug("회원 단건 조회: id={}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 회원 조회 시도: id={}", id);
                    return new IllegalArgumentException("존재하지 않는 회원입니다.");
                });
        return MemberResponse.from(member);
    }

    private void validateDuplicateMember(String name) {
        memberRepository.findByName(name).ifPresent(m -> {
            log.warn("중복 회원 가입 시도: name={}", name);
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
