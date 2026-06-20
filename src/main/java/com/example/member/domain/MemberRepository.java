package com.example.member.domain;

// ===== 기존 코드 =====
// public interface MemberRepository {
//     Member save(Member member);
//     Optional<Member> findByID(Long id);
//     Optional<Member> findByName(String name);
//     List<Member> findAll();
// }

// ===== 최신 구조 =====
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}
