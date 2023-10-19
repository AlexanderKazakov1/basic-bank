package com.kazakov.basic.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazakov.basic.bank.dto.AccountRequest;
import com.kazakov.basic.bank.dto.TransferRequest;
import com.kazakov.basic.bank.entity.Account;
import com.kazakov.basic.bank.entity.Transfer;
import com.kazakov.basic.bank.repository.AccountRepository;
import com.kazakov.basic.bank.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Test
    void testCreateAccount() throws Exception {
        AccountRequest request = new AccountRequest(123L, "Test Account", BigDecimal.valueOf(100.50));
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName").value("Test Account"))
                .andExpect(jsonPath("$.customerId").value(123))
                .andExpect(jsonPath("$.balance").value(100.50))
                .andExpect(jsonPath("$.accountId").value(1));
    }

    @Test
    void testGetAccountBalance() throws Exception {
        Account account = Account.builder()
                .name("Test Account")
                .customerId(123L)
                .balance(BigDecimal.valueOf(100.50))
                .build();
        accountRepository.save(account);
        mockMvc.perform(get("/api/v1/accounts/1/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.balance").exists());
    }

    @Test
    void testGetTransferHistory() throws Exception {
        Account account = Account.builder()
                .name("Test Account")
                .customerId(123L)
                .balance(BigDecimal.valueOf(100.50))
                .build();
        accountRepository.save(account);
        Transfer transfer = Transfer.builder()
                .fromAccountId(1L)
                .toAccountId(1L)
                .amount(BigDecimal.TEN)
                .timestamp(LocalDateTime.now())
                .build();
        transferRepository.save(transfer);
        mockMvc.perform(get("/api/v1/accounts/1/transfers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.transfers").exists());
    }

    @Test
    void testTransferAmount() throws Exception {
        Account firstAccount = Account.builder()
                .name("First Test Account")
                .customerId(123L)
                .balance(BigDecimal.valueOf(100.50))
                .build();
        accountRepository.save(firstAccount);

        Account secondAccount = Account.builder()
                .name("Second Test Account")
                .customerId(321L)
                .balance(BigDecimal.valueOf(100.50))
                .build();
        accountRepository.save(secondAccount);

        TransferRequest request = new TransferRequest(1L, 2L, BigDecimal.valueOf(50));
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromAccountId").value(1))
                .andExpect(jsonPath("$.toAccountId").value(2))
                .andExpect(jsonPath("$.amount").value(50));
    }

}