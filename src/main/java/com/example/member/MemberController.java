package com.example.member;

// ===== 기존 코드 =====
// @Controller
// public class MemberController {
//     private final MemberService memberService;
//
//     public MemberController(MemberService memberService) {
//         this.memberService = memberService;
//     }
//
//     @GetMapping("/members/new")
//     public String createForm() {
//         return "members/createMemberForm";
//     }
//
//     @PostMapping("/members/new")
//     public String create(MemberForm form) {
//         Member member = new Member();
//         member.setName(form.getName());
//         memberService.join(member);
//         return "redirect:/members";
//     }
//
//     @GetMapping("/members")
//     public String list(Model model) {
//         model.addAttribute("members", memberService.findMembers());
//         return "members/memberList";
//     }
// }

// ===== 최신 구조 =====
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody @jakarta.validation.Valid MemberRequest request) {
        return ResponseEntity.ok(memberService.join(request));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> list() {
        return ResponseEntity.ok(memberService.findMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findOne(id));
    }
}
