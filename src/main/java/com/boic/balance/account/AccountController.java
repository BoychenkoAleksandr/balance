package com.boic.balance.account;

import com.boic.balance.coniguration.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/transfer")
    public ResponseEntity<AccountDtoOut> transfer(
            @RequestBody @Valid TransferDtoIn transferEntity,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Account account = accountService.transferMoney(customUserDetails.getId(), transferEntity.getToUserId(), transferEntity.getAmount());
        return ResponseEntity.ok(accountMapper.toOut(account));
    }
}
