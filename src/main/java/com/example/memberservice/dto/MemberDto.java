package com.example.memberservice.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String memberId;
    private String email;
    private String name;
    private String password;
    private String encryptedPassword;
}
