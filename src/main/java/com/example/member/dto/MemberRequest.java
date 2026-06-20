package com.example.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberRequest(
        @NotBlank(message = "이름은 필수입니다.") String name,
        @NotBlank @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
        @NotBlank @Size(min = 8, message = "비밀번호는 8자 이상입니다.") String password
) {}
