package com.example.memberservice.vo;

import com.example.memberservice.enums.Rule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenJoinAuthority {
    private String gatherId;
    private Rule rule;
}
