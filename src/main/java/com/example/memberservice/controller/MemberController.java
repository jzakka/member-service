package com.example.memberservice.controller;

import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.service.MemberService;
import com.example.memberservice.vo.RequestMember;
import com.example.memberservice.vo.ResponseMember;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper mapper;
    private final Environment env;

    @PostMapping("/members")
    public ResponseEntity<ResponseMember> createMember(@RequestBody RequestMember requestMember) {
        MemberDto memberDto = mapper.map(requestMember, MemberDto.class);

        MemberDto createdMember = memberService.createMember(memberDto);

        ResponseMember body = mapper.map(createdMember, ResponseMember.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseMember> getMember(@PathVariable String memberId) {
        MemberDto memberDto = memberService.getMemberByMemberId(memberId);

        ResponseMember body = mapper.map(memberDto, ResponseMember.class);

        return ResponseEntity.ok(body);
    }

}
