package com.boic.balance.account;

import com.boic.balance.exception.AccountException;
import com.boic.balance.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.10");
    private static final BigDecimal MAX_MULTIPLIER = new BigDecimal("2.07");

    private final AccountRepository accountRepository;
    private final AccountJpaMapper accountJpaMapper;

    @Transactional
    public Account transferMoney(Long fromUserId, Long toUserId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("Transfer amount must be positive");
        }

        if (toUserId == null) {
            throw new AccountException("Recipient ID is required");
        }

        AccountJpa fromAccount = accountRepository.findByUserIdWithLock(fromUserId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        AccountJpa toAccount = accountRepository.findByUserIdWithLock(toUserId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new AccountException("Not enough money");
        }

        BigDecimal newToBalance = toAccount.getBalance().add(amount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(newToBalance);

        accountRepository.saveAll(List.of(fromAccount, toAccount));

        return accountJpaMapper.fromJpaEntity(fromAccount);

    }

    @Scheduled(fixedRate = 30000)
    public void accrueInterest() {
        List<AccountJpa> accounts = accountRepository.findAll();

        for (AccountJpa account : accounts) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal initialBalance = account.getStartBalance();
            BigDecimal maxAllowedBalance = initialBalance.multiply(MAX_MULTIPLIER);

            if (currentBalance.compareTo(maxAllowedBalance) < 0) {
                BigDecimal interest = currentBalance.multiply(INTEREST_RATE);
                BigDecimal newBalance = currentBalance.add(interest).min(maxAllowedBalance);

                account.setBalance(newBalance);
                accountRepository.save(account);
            }
        }
    }
}
