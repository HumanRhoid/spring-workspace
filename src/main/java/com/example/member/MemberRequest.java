package com.example.member;

// ===== 기존 코드 (MemberForm 대체) =====
// public class MemberForm {
//     private String name;
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
// }

// ===== 최신 구조 =====
import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank(message = "이름은 필수입니다.") String name
) {}
