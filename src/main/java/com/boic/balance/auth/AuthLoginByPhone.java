package com.boic.balance.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthLoginByPhone {
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$")
    private String login;
    @NotNull
    private String password;
}
