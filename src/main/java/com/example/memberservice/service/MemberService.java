package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto getMemberByMemberId(String memberId);

    MemberDto getMemberByEmail(String email);

    List<MemberDto> getMemberByName(String name);
}
