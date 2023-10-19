package com.kazakov.basic.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @Min(1)
        @NotNull
        Long fromAccountId,
        @Min(1)
        @NotNull
        Long toAccountId,
        @Min(1)
        @NotNull
        BigDecimal amount
) {

}
