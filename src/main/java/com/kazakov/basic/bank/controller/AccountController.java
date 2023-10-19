package com.kazakov.basic.bank.controller;

import com.kazakov.basic.bank.dto.AccountBalanceDto;
import com.kazakov.basic.bank.dto.AccountDto;
import com.kazakov.basic.bank.dto.AccountRequest;
import com.kazakov.basic.bank.dto.TransferDto;
import com.kazakov.basic.bank.dto.TransferHistoryDto;
import com.kazakov.basic.bank.dto.TransferRequest;
import com.kazakov.basic.bank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts"
)
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping(value = "/{accountId}/balance")
    public ResponseEntity<AccountBalanceDto> getAccountBalance(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountBalance(accountId));
    }

    @GetMapping("/{accountId}/transfers")
    public ResponseEntity<TransferHistoryDto> getTransferHistory(@Valid @PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getTransferHistory(accountId));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferDto> transferAmount(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.transferAmount(request));
    }

}
