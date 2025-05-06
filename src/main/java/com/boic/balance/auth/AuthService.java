package com.boic.testTask.auth;

import com.boic.testTask.configuration.CustomUserDetails;
import com.boic.testTask.configuration.CustomUserDetailsService;
import com.boic.testTask.configuration.JwtTokenUtil;
import com.boic.testTask.exception.LoginException;
import com.boic.testTask.exception.RegisterException;
import com.boic.testTask.users.*;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Сервис для регистрации и аутентификации пользователей.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Регистрацию новых пользователей с автоматической генерацией пароля.</li>
 *   <li>Аутентификацию через JWT-токены.</li>
 * </ul>
 * Пароли хранятся в зашифрованном виде (BCrypt).
 * </p>
 *
 * @author Boichenko
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * Генерирует случайный пароль (12 символов: буквы, цифры, спецсимволы),
     * шифрует его и сохраняет пользователя в БД.
     * </p>
     *
     * @param user сущность пользователя (должна содержать уникальный username)
     * @return {@link UserDtoOut} — данные пользователя с <b>незашифрованным</b> паролем (для вывода)
     * @throws RuntimeException если пользователь с таким username уже существует
     * @implNote Логирование:
     * <ul>
     *   <li>DEBUG: попытка регистрации.</li>
     *   <li>WARN: конфликт username.</li>
     *   <li>INFO: успешная регистрация.</li>
     * </ul>
     */
    public UserDtoOut registerUser(User user) {
        log.debug("Attempting to register new user: {}", user.getUsername());
        if (userService.findByUsernameIgnoreCase(user.getUsername()) != null) {
            log.warn("Registration failed - username already exists: {}", user.getUsername());
            throw new RegisterException("Username already exists");
        }

        // Generate random password
        String generatedPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));

        User savedUser = userService.persist(user);
        log.info("User registered successfully: {}", savedUser.getUsername());
        UserDtoOut userDtoOut = userMapper.toOut(savedUser);
        userDtoOut.setPassword(generatedPassword);
        return userDtoOut;
    }

    /**
     * Генерирует случайный пароль.
     *
     * @return строка из 12 символов (A-Z, a-z, 0-9, спецсимволы)
     * @implSpec Алгоритм: выбирает случайные символы из предопределённого набора.
     */
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Аутентифицирует пользователя и возвращает JWT-токен.
     *
     * @param username логин пользователя
     * @param password пароль (в открытом виде)
     * @return JWT-токен для доступа к защищённым ресурсам
     * @throws AuthException если аутентификация не удалась (неверные данные)
     * @implNote Логирование:
     * <ul>
     *   <li>DEBUG: попытка входа.</li>
     *   <li>ERROR: ошибка аутентификации.</li>
     *   <li>INFO: успешный вход.</li>
     * </ul>
     */
    public String login(String username, String password) throws AuthException {
        log.debug("Attempting to authenticate user: {}", username);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

            CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtTokenUtil.generateToken(customUserDetails);
            log.info("User {} successfully authenticated", username);
            return token;
        } catch (Exception e) {
            log.error("Authentication failed for user {}: {}", username, e.getMessage());
            throw new LoginException("Invalid login or password");
        }
    }
}
