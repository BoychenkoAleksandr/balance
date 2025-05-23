package com.boic.balance.phone;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneDto {
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$")
    private String phone;
}
