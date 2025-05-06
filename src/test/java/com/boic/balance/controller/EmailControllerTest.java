package com.boic.balance.controller;

import com.boic.balance.coniguration.CustomUserDetails;
import com.boic.balance.email.*;
import com.boic.balance.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {
    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @Mock
    private EmailMapper emailMapper;

    // Тестовые данные
    private final Long testId = 1L;
    private EmailDto emailDto;
    private Email email;
    private CustomUserDetails userDetails;
    @BeforeEach
    void setUp() {
        emailDto = new EmailDto();
        emailDto.setEmail("11111111113");

        email = new Email();
        email.setId(1L);
        email.setEmail("11111111113");
        User user = new User();
        user.setId(10L);
        email.setUser(user);

        emailDto = new EmailDto();
        emailDto.setEmail("11111111113");

        userDetails = new CustomUserDetails(
                1L, "testUser", "password", null);
    }

    @Test
    void getEmailById_ShouldReturnEmail_WhenEmailExists() {
        // Подготовка
        when(emailService.getById(testId)).thenReturn(email);
        when(emailMapper.toOut(email)).thenReturn(emailDto);

        // Действие
        ResponseEntity<EmailDto> response = emailController.getEmailById(testId);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailDto, response.getBody());

        // Проверка вызовов
        verify(emailService, times(1)).getById(testId);
        verify(emailMapper, times(1)).toOut(email);
    }
    @Test
    void createEmail_ShouldReturnCreatedEmail() {
        // Подготовка
        when(emailMapper.fromIn(emailDto)).thenReturn(email);
        when(emailService.persistUniqueMail(email)).thenReturn(email);
        when(emailMapper.toOut(email)).thenReturn(emailDto);

        // Действие
        ResponseEntity<EmailDto> response =
                emailController.createEmail(emailDto, userDetails);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(emailDto, response.getBody());

        // Проверка вызовов
        verify(emailMapper, times(1)).fromIn(emailDto);
        verify(emailService, times(1)).persistUniqueMail(email);
        verify(emailMapper, times(1)).toOut(email);
    }

    @Test
    void updateEmail_ShouldReturnUpdatedEmail() {
        // Подготовка
        when(emailMapper.fromIn(emailDto)).thenReturn(email);
        when(emailService.updateEmail(
                eq(testId),
                eq(email),
                eq(userDetails.getId()))
        ).thenReturn(email);
        when(emailMapper.toOut(email)).thenReturn(emailDto);

        // Действие
        ResponseEntity<EmailDto> response =
                emailController.updateEmail(testId, emailDto, userDetails);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailDto, response.getBody());

        // Проверка вызовов
        verify(emailMapper, times(1)).fromIn(emailDto);
        verify(emailService, times(1)).updateEmail(
                eq(testId),
                eq(email),
                eq(userDetails.getId()));
        verify(emailMapper, times(1)).toOut(email);
    }

    @Test
    void deleteEmail_ShouldInvokeService_WhenUserHasPermission() {
        // Действие
        emailController.deleteEmail(testId, userDetails);

        // Проверка
        verify(emailService, times(1)).delete(
                eq(testId),
                eq(userDetails.getId()));
    }
}

