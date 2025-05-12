package com.boic.balance.email;

import com.boic.balance.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Email implements Serializable {
    private Long id;
    private User user;
    private String email;
}
