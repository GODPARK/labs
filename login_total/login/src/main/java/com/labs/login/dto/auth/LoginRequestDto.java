package com.labs.login.dto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
}
