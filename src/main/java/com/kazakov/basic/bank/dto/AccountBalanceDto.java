package com.kazakov.basic.bank.dto;

import java.math.BigDecimal;

public record AccountBalanceDto(Long accountId, BigDecimal balance) {
}
