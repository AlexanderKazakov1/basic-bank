package com.kazakov.basic.bank.service.impl;


import com.kazakov.basic.bank.dto.AccountDto;
import com.kazakov.basic.bank.dto.AccountRequest;
import com.kazakov.basic.bank.dto.TransferDto;
import com.kazakov.basic.bank.dto.TransferRequest;
import com.kazakov.basic.bank.entity.Account;
import com.kazakov.basic.bank.entity.Transfer;
import com.kazakov.basic.bank.mapper.AccountMapper;
import com.kazakov.basic.bank.repository.AccountRepository;
import com.kazakov.basic.bank.repository.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        var customerId = 1L;
        var name = "Name";
        var initialDeposit = BigDecimal.TEN;
        AccountRequest request = new AccountRequest(customerId, name, initialDeposit);
        Account account = new Account(null, customerId, name, initialDeposit);

        accountService.createAccount(request);

        verify(accountRepository, times(1)).save(account);
        verify(mapper, times(1)).toAccountCreationResult(account);
    }

    @Test
    void testTransferAmount() {
        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.TEN);
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(20));
        fromAccount.setName("from");
        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.ZERO);
        toAccount.setName("to");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
        when(mapper.toTransferDto(any(Transfer.class))).thenReturn(mock(TransferDto.class));

        TransferDto result = accountService.transferAmount(request);

        assertEquals(BigDecimal.valueOf(10), fromAccount.getBalance());
        assertEquals(BigDecimal.TEN, toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
        verify(transferRepository, times(1)).save(any(Transfer.class));
        assertNotNull(result);
    }

    @Test
    void testTransferAmount_ZeroAmount() {
        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.ZERO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.transferAmount(request));

        assertEquals("The transfer amount must be greater than zero", exception.getMessage());
    }

    @Test
    void testTransferAmount_FromAccountNotFound() {
        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.TEN);
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> accountService.transferAmount(request));

        assertEquals("Account not found for id: 1", exception.getMessage());
    }

    @Test
    void testTransferAmount_ToAccountNotFound() {
        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.TEN);
        Account fromAccount = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> accountService.transferAmount(request));

        assertEquals("Account not found for id: 2", exception.getMessage());
    }

    @Test
    void testTransferAmount_NotEnoughFunds() {
        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.TEN);
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.ONE);
        Account toAccount = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.transferAmount(request));

        assertEquals("There are not enough funds in the account for the transfer", exception.getMessage());
    }

    @Test
    void testGetAccountBalance() {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.getAccountBalance(1L);

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransferHistory() {
        accountService.getTransferHistory(1L);
        verify(transferRepository, times(1)).findByFromAccountId(1L);
    }
}