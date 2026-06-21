package com.example.domain.member.mapper;

import com.example.domain.member.dto.MemberRequest;
import com.example.domain.member.dto.MemberResponse;
import com.example.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberRequest request) {
        return new Member(request.name(), request.email(), request.password());
    }

    public MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt()
        );
    }
}
