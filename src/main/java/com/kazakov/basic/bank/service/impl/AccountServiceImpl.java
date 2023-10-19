package com.kazakov.basic.bank.service.impl;

import com.kazakov.basic.bank.dto.AccountBalanceDto;
import com.kazakov.basic.bank.dto.AccountDto;
import com.kazakov.basic.bank.dto.AccountRequest;
import com.kazakov.basic.bank.dto.TransferDto;
import com.kazakov.basic.bank.dto.TransferHistoryDto;
import com.kazakov.basic.bank.dto.TransferRequest;
import com.kazakov.basic.bank.entity.Account;
import com.kazakov.basic.bank.entity.Transfer;
import com.kazakov.basic.bank.mapper.AccountMapper;
import com.kazakov.basic.bank.repository.AccountRepository;
import com.kazakov.basic.bank.repository.TransferRepository;
import com.kazakov.basic.bank.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    public AccountDto createAccount(AccountRequest request) {
        Account account = Account.builder()
                .name(request.name())
                .customerId(request.customerId())
                .balance(request.initialDeposit())
                .build();
        accountRepository.save(account);
        return mapper.toAccountCreationResult(account);
    }

    @Override
    @Transactional
    public TransferDto transferAmount(TransferRequest request) {
        BigDecimal amount = request.amount();
        if (amount.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("The transfer amount must be greater than zero");
        }
        Account fromAccount = accountRepository.findById(request.fromAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id: " + request.fromAccountId()));

        Account toAccount = accountRepository.findById(request.toAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id: " + request.toAccountId()));

        if (isEnoughFundsInAccount(fromAccount, amount)) {
            throw new IllegalArgumentException("There are not enough funds in the account for the transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transfer transfer = Transfer.builder()
                .fromAccountId(fromAccount.getId())
                .toAccountId(toAccount.getId())
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
        transferRepository.save(transfer);

        return mapper.toTransferDto(transfer);
    }

    @Override
    public AccountBalanceDto getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id: " + accountId));
        return new AccountBalanceDto(accountId, account.getBalance());
    }

    @Override
    public TransferHistoryDto getTransferHistory(Long accountId) {
        List<Transfer> transfers = transferRepository.findByFromAccountId(accountId);
        return mapper.toTransferHistory(accountId, transfers);
    }

    private boolean isEnoughFundsInAccount(Account fromAccount, BigDecimal amount) {
        return fromAccount.getBalance().compareTo(amount) < 0;
    }
}
