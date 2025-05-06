package com.boic.balance.user;

import lombok.Data;

@Data
public class UserDtoIn {
    private String username = "";
    private String phone = "";
    private String email = "";
    private String dateOfBirth = "";
}
