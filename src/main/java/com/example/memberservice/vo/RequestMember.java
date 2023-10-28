package com.example.memberservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestMember {
    @NotNull(message = "${member.email.notnull-msg}")
    @Size(min = 2, message = "${member.email.size-msg}")
    @Email
    private String email;

    @NotNull(message = "${member.email.notnull-msg}")
    @Size(min = 2, message = "${member.email.size-msg}")
    private String name;

    @NotNull(message = "${member.password.notnull-msg}")
    @Size(min=8, message = "${member.password.size-msg}")
    private String password;
}
