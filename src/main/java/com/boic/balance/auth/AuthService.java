package com.boic.balance.auth;

import com.boic.balance.coniguration.CustomUserDetails;
import com.boic.balance.coniguration.CustomUserDetailsService;
import com.boic.balance.coniguration.JwtTokenUtil;
import com.boic.balance.email.EmailService;
import com.boic.balance.exception.LoginException;
import com.boic.balance.phone.PhoneService;
import com.boic.balance.user.User;
import com.boic.balance.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;
    private final UserService userService;
    private final PhoneService phoneService;

    public String loginByEmail(String email, String password) {
        log.debug("Attempting to authenticate user with email: {}", email);
        try {
            Long userId = emailService.findByEmail(email).getUser().getId();
            User user = userService.getById(userId);
            if (!user.getPassword().equals(password))
                throw new RuntimeException();
            CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtTokenUtil.generateToken(customUserDetails);
            log.info("User with email {} successfully authenticated", email);
            return token;
        } catch (Exception e) {
            log.error("Authentication failed for user with email {}: {}", email, e.getMessage());
            throw new LoginException("Invalid login or password");
        }
    }

    public String loginByPhone(String phone, String password) {
        log.debug("Attempting to authenticate user with phone: {}", phone);
        try {
            Long userId = phoneService.findByPhone(phone).getUser().getId();
            User user = userService.getById(userId);
            if (!user.getPassword().equals(password))
                throw new RuntimeException();

            CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtTokenUtil.generateToken(customUserDetails);
            log.info("User with phone {} successfully authenticated", phone);
            return token;
        } catch (Exception e) {
            log.error("Authentication failed for user with phone {}: {}", phone, e.getMessage());
            throw new LoginException("Invalid login or password");
        }
    }
}
