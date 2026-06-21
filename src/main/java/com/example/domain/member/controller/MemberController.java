package com.example.domain.member.controller;

import com.example.domain.member.dto.MemberRequest;
import com.example.domain.member.dto.MemberResponse;
import com.example.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberRequest request) {
        log.info("POST /api/v1/members - name={}", request.name());
        return ResponseEntity.ok(memberService.join(request));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> list() {
        log.info("GET /api/v1/members");
        return ResponseEntity.ok(memberService.findMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable Long id) {
        log.info("GET /api/v1/members/{}", id);
        return ResponseEntity.ok(memberService.findOne(id));
    }
}
