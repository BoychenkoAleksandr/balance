package com.boic.balance.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
}
