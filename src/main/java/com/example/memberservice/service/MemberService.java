package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MemberService {
    MemberDto getMemberByMemberId(String memberId);

    MemberDto getMemberByEmail(String email);

    List<MemberDto> getMemberByName(String name);

    MemberDto createUser(MemberDto memberDto);

    UserDetails getUserDetailsByEmail(String email);
}
