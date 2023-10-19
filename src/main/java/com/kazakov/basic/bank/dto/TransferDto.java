package com.kazakov.basic.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferDto(Long transferId, Long fromAccountId, Long toAccountId, BigDecimal amount,
                          LocalDateTime timestamp) {
}
