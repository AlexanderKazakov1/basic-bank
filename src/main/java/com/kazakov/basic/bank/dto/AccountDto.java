package com.kazakov.basic.bank.dto;

import java.math.BigDecimal;

public record AccountDto(Long accountId, Long customerId, String accountName, BigDecimal balance) {
}
