package com.boic.testTask.users;

import com.boic.testTask.common.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String parentName;
    private LocalDateTime creationDate;
    private LocalDateTime lastEditDate;
    private Role role;
}
