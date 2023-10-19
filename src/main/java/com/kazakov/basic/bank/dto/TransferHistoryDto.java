package com.kazakov.basic.bank.dto;

import java.util.List;

public record TransferHistoryDto(Long accountId, List<TransferDto> transfers) {
}
