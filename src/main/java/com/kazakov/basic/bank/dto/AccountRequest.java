package com.kazakov.basic.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRequest(
        @Min(1)
        @NotNull
        Long customerId,
        @NotBlank(message = "'name' is mandatory")
        String name,
        @Min(1)
        @NotNull
        BigDecimal initialDeposit
) {
}
