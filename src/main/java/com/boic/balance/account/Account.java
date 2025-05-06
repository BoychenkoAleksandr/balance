package com.boic.balance.account;

import com.boic.balance.user.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private Long id;
    private User user;
    private BigDecimal startBalance;
    private BigDecimal balance;
}
