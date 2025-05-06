package com.boic.balance.phone;

import com.boic.balance.user.User;
import lombok.Data;

@Data
public class Phone {
    private Long id;
    private User user;
    private String phone;
}
