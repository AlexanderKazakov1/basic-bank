package com.kazakov.basic.bank.service;

import com.kazakov.basic.bank.dto.*;
import com.kazakov.basic.bank.dto.TransferHistoryDto;

/**
 * Service for working with client accounts
 */
public interface AccountService {

    /**
     * Create a new bank account for a customer, with an initial deposit amount.
     *
     * @param request account information
     * @return creation result
     */
    AccountDto createAccount(AccountRequest request);

    /**
     * Transfer amounts between any two accounts.
     *
     * @param request transfer information
     * @return transfer result
     */
    TransferDto transferAmount(TransferRequest request);

    /**
     * Retrieve balance for a given account.
     *
     * @param accountId account id
     * @return account balance
     */
    AccountBalanceDto getAccountBalance(Long accountId);

    /**
     * Retrieve transfer history for a given account.
     *
     * @param accountId account id
     * @return transfer history
     */
    TransferHistoryDto getTransferHistory(Long accountId);
}
