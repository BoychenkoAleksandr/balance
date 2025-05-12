package com.boic.balance.controller;

import com.boic.balance.coniguration.CustomUserDetails;
import com.boic.balance.phone.*;
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
public class PhoneControllerTest {
    @Mock
    private PhoneService phoneService;

    @InjectMocks
    private PhoneController phoneController;

    @Mock
    private PhoneMapper phoneMapper;

    // Тестовые данные
    private final Long testId = 1L;
    private PhoneDto phoneDto;
    private Phone phone;
    private User user;
    private CustomUserDetails userDetails;
    @BeforeEach
    void setUp() {
        phoneDto = new PhoneDto();
        phoneDto.setPhone("11111111113");

        phone = new Phone();
        phone.setId(1L);
        phone.setPhone("11111111113");
        user = new User();
        user.setId(10L);
        phone.setUser(user);

        phoneDto = new PhoneDto();
        phoneDto.setPhone("11111111113");

        userDetails = new CustomUserDetails(
                1L, "testUser", "password", null);
    }

    @Test
    void getPhoneById_ShouldReturnPhone_WhenPhoneExists() {
        // Подготовка
        when(phoneService.getById(testId)).thenReturn(phone);
        when(phoneMapper.toOut(phone)).thenReturn(phoneDto);

        // Действие
        ResponseEntity<PhoneDto> response = phoneController.getPhoneById(testId);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(phoneDto, response.getBody());

        // Проверка вызовов
        verify(phoneService, times(1)).getById(testId);
        verify(phoneMapper, times(1)).toOut(phone);
    }
    @Test
    void createPhone_ShouldReturnCreatedPhone() {
        // Подготовка
        when(phoneMapper.fromIn(phoneDto)).thenReturn(phone);
        when(phoneService.persistUniquePhone(phone)).thenReturn(phone);
        when(phoneMapper.toOut(phone)).thenReturn(phoneDto);

        // Действие
        ResponseEntity<PhoneDto> response =
                phoneController.createPhone(phoneDto, userDetails);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(phoneDto, response.getBody());

        // Проверка вызовов
        verify(phoneMapper, times(1)).fromIn(phoneDto);
        verify(phoneService, times(1)).persistUniquePhone(phone);
        verify(phoneMapper, times(1)).toOut(phone);
    }

    @Test
    void updatePhone_ShouldReturnUpdatedPhone() {
        // Подготовка
        Phone newPhone = new Phone();
        newPhone.setId(1L);
        newPhone.setPhone("11111111114");
        phone.setUser(user);
        when(phoneMapper.fromIn(phoneDto)).thenReturn(phone);
        when(phoneService.updatePhone(
                eq(testId),
                eq(phone),
                eq(userDetails.getId()))
        ).thenReturn(phone);
        when(phoneMapper.toOut(phone)).thenReturn(phoneDto);

        // Действие
        ResponseEntity<PhoneDto> response =
                phoneController.updatePhone(testId, phoneDto, userDetails);

        // Проверка
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(phoneDto, response.getBody());

        // Проверка вызовов
        verify(phoneMapper, times(1)).fromIn(phoneDto);
        verify(phoneService, times(1)).updatePhone(
                eq(testId),
                eq(phone),
                eq(userDetails.getId()));
        verify(phoneMapper, times(1)).toOut(phone);
    }

    @Test
    void deletePhone_ShouldInvokeService_WhenUserHasPermission() {
        // Действие
        phoneController.deletePhone(testId, userDetails);

        // Проверка
        verify(phoneService, times(1)).delete(
                eq(testId),
                eq(userDetails.getId()));
    }
}
