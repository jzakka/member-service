package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.entity.MemberEntity;
import com.example.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto getMemberByMemberId(String memberId) {
        MemberEntity memberEntity = memberRepository
                .findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(env.getProperty("member.not-found-msg")));

        return mapper.map(memberEntity, MemberDto.class);
    }

    @Override
    public MemberDto getMemberByEmail(String email) {
        MemberEntity memberEntity = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(env.getProperty("member.not-found-msg")));

        return mapper.map(memberEntity, MemberDto.class);
    }

    @Override
    public List<MemberDto> getMemberByName(String name) {
        List<MemberEntity> members = memberRepository.findByName(name);

        return members.stream().map(entity -> mapper.map(entity, MemberDto.class)).toList();
    }

    @Override
    public MemberDto createUser(MemberDto memberDto) {
        memberDto.setMemberId(UUID.randomUUID().toString());

        MemberEntity memberEntity = mapper.map(memberDto, MemberEntity.class);
        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberEntity.setEncryptedPassword(encryptedPassword);

        memberRepository.save(memberEntity);

        return memberDto;
    }

    @Override
    public UserDetails getUserDetailsByEmail(String email) {
        MemberEntity memberEntity = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(env.getProperty("member.not-found-msg")));

        return new User(memberEntity.getEmail(), memberEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
