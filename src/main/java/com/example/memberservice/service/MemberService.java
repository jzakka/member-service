package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    MemberDto getMemberByMemberId(String memberId);

    MemberDto getMemberByEmail(String email);

    List<MemberDto> getMemberByName(String name);

    MemberDto createMember(MemberDto memberDto);
}
