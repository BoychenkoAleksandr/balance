package com.boic.balance.controller;

import com.boic.balance.account.*;
import com.boic.balance.coniguration.CustomUserDetails;
import com.boic.balance.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountMapper accountMapper;

    @Test
    void transfer_ShouldReturnAccountDto_WhenSuccessful() {
        // Arrange
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = new BigDecimal("100.00");

        TransferDtoIn transferDto = new TransferDtoIn();
        transferDto.setAmount(amount);
        transferDto.setToUserId(toUserId);
        CustomUserDetails userDetails = new CustomUserDetails(
                fromUserId, "user", "password", Collections.emptyList());

        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("500.00"));

        AccountDtoOut accountDtoOut = new AccountDtoOut();
        accountDtoOut.setBalance(new BigDecimal("500.00"));

        when(accountService.transferMoney(fromUserId, toUserId, amount))
                .thenReturn(account);
        when(accountMapper.toOut(account))
                .thenReturn(accountDtoOut);

        // Act
        ResponseEntity<AccountDtoOut> response = accountController
                .transfer(transferDto, userDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDtoOut, response.getBody());
        verify(accountService).transferMoney(fromUserId, toUserId, amount);
    }

    @Test
    void transfer_ShouldThrowException_WhenServiceThrows() {
        // Arrange
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = new BigDecimal("1000.00"); // Сумма превышает баланс

        TransferDtoIn transferDto = new TransferDtoIn();
        transferDto.setAmount(amount);
        transferDto.setToUserId(toUserId);
        CustomUserDetails userDetails = new CustomUserDetails(
                fromUserId, "user", "password", Collections.emptyList());

        when(accountService.transferMoney(fromUserId, toUserId, amount))
                .thenThrow(new AccountException("Not enough money"));

        // Act & Assert
        assertThrows(AccountException.class, () ->
                accountController.transfer(transferDto, userDetails));
    }
}
