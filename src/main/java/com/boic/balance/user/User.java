package com.boic.balance.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
}
