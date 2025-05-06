package com.boic.balance.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDtoIn {
    @NotNull
    private Long toUserId;

    @NotNull @Positive
    private BigDecimal amount;
}
