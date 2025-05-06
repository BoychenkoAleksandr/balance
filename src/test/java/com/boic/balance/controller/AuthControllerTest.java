package com.boic.balance.controller;

import com.boic.balance.auth.AuthLogin;
import com.boic.balance.auth.AuthService;
import com.boic.balance.auth.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;
    private AuthLogin testLoginByEmail;
    private AuthLogin testLoginByPhone;

    @BeforeEach
    void setUp() {

        testLoginByEmail = new AuthLogin();
        testLoginByEmail.setLogin("test@example.com");
        testLoginByEmail.setPassword("password");

        testLoginByPhone = new AuthLogin();
        testLoginByPhone.setLogin("11111111111");
        testLoginByPhone.setPassword("password");

    }
    @Test
    void loginByEmail_ShouldReturnToken_WhenCredentialsValid() {
        // Подготовка
        String testToken = "test.jwt.token";
        when(authService.loginByEmail(testLoginByEmail.getLogin(), testLoginByEmail.getPassword()))
                .thenReturn(testToken);

        // Действие
        String result = authController.loginByEmail(testLoginByEmail);

        // Проверка
        assertEquals(testToken, result);

        // Проверка вызовов
        verify(authService, times(1)).loginByEmail(testLoginByEmail.getLogin(), testLoginByEmail.getPassword());
    }

    @Test
    void loginByPhone_ShouldReturnToken_WhenCredentialsValid() {
        // Подготовка
        String testToken = "test.jwt.token";
        when(authService.loginByPhone(testLoginByPhone.getLogin(), testLoginByPhone.getPassword()))
                .thenReturn(testToken);

        // Действие
        String result = authController.loginByPhone(testLoginByPhone);

        // Проверка
        assertEquals(testToken, result);

        // Проверка вызовов
        verify(authService, times(1)).loginByPhone(testLoginByPhone.getLogin(), testLoginByPhone.getPassword());
    }
}
