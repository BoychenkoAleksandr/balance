package com.boic.balance.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/loginByEmail")
    public String loginByEmail(@RequestBody AuthLogin authLogin) {
        return authService.loginByEmail(authLogin.getLogin(), authLogin.getPassword());
    }

    @GetMapping("/loginByPhone")
    public String loginByPhone(@RequestBody AuthLogin authLogin) {
        return authService.loginByPhone(authLogin.getLogin(), authLogin.getPassword());
    }
}
