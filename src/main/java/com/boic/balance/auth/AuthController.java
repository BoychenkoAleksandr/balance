package com.boic.balance.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/loginByEmail")
    public String loginByEmail(@RequestBody @Valid AuthLoginByEmail authLogin) {
        return authService.loginByEmail(authLogin.getLogin(), authLogin.getPassword());
    }

    @GetMapping("/loginByPhone")
    public String loginByPhone(@RequestBody @Valid AuthLoginByPhone authLogin) {
        return authService.loginByPhone(authLogin.getLogin(), authLogin.getPassword());
    }
}
