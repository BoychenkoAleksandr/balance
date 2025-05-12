package com.boic.balance.phone;

import com.boic.balance.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Phone implements Serializable {
    private Long id;
    private User user;
    private String phone;
}
