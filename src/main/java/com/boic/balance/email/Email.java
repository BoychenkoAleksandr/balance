package com.boic.balance.email;

import com.boic.balance.user.User;
import lombok.Data;

@Data
public class Email {
    private Long id;
    private User user;
    private String email;
}
