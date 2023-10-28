package com.example.memberservice.repository;

import com.example.memberservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberId(String memberId);

    Optional<MemberEntity> findByEmail(String email);

    List<MemberEntity> findByName(String name);
}
