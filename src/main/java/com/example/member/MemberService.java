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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse join(MemberRequest request) {
        validateDuplicateMember(request.name());
        Member member = new Member(request.name());
        memberRepository.save(member);
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return MemberResponse.from(member);
    }

    private void validateDuplicateMember(String name) {
        memberRepository.findByName(name).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
