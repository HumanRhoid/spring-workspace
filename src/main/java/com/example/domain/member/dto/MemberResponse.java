package com.example.domain.member.dto;

import java.time.LocalDateTime;

public record MemberResponse(Long id, String name, String email, LocalDateTime createdAt) {}
