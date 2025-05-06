package com.boic.testTask.auth;

import com.boic.testTask.users.UserDtoOut;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для аутентификации и регистрации пользователей.
 * <p>
 * Предоставляет REST-эндпоинты для регистрации новых пользователей и входа в систему.
 * Все методы работают с JSON-данными.
 * </p>
 *
 * @author Boichenko
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param authRegister DTO с данными для регистрации (логин, ФИО, роль)
     * @return {@link UserDtoOut} — данные зарегистрированного пользователя (логин, пароль)
     * @apiNote Пример запроса:
     * <pre>
     * POST /api/auth/register
     * {
     *   "username": "user123",
     *   "name": "Иван",
     *   "surname": "Иванов",
     *   "parentName": "Иванович",
     *   "role": "ROLE_ADMIN"
     * }
     * </pre>
     * @response code 200 Пользователь успешно создан
     * @response code 400 Некорректные данные (например, пароль слишком короткий)
     */
    @PostMapping("/register")
    public ResponseEntity<UserDtoOut> registerUser(@RequestBody AuthRegister authRegister) {
        UserDtoOut newUser = authService.registerUser(authMapper.fromAuthRegister(authRegister));
        return ResponseEntity.ok(newUser);
    }

    /**
     * Аутентифицирует пользователя и возвращает токен.
     *
     * @param authLogin DTO с логином и паролем
     * @return токен для доступа к защищённым эндпоинтам
     * @throws AuthException если аутентификация не удалась (неверные данные)
     * @apiNote Пример запроса:
     * <pre>
     * POST /api/auth/login
     * {
     *   "username": "user123",
     *   "password": "qwerty"
     * }
     * </pre>
     * @response code 200 Успешный вход, возвращает токен
     * @response code 401 Неверные логин/пароль
     */
    @PostMapping("/login")
    public String login(@RequestBody AuthLogin authLogin) throws AuthException {
        return authService.login(authLogin.getUsername(), authLogin.getPassword());
    }
}
