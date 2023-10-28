package com.example.memberservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {
    @NotNull(message = "${login.email.notnull-msg}")
    @Size(min = 2, message = "${login.email.size-msg}")
    @Email
    private String email;

    @NotNull(message = "${login.password.notnull-msg}")
    @Size(min=8, message = "${login.password.size-msg}")
    private String password;
}
