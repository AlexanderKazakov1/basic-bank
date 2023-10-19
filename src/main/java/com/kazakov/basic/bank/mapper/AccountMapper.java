package com.kazakov.basic.bank.mapper;

import com.kazakov.basic.bank.dto.AccountDto;
import com.kazakov.basic.bank.dto.TransferDto;
import com.kazakov.basic.bank.dto.TransferHistoryDto;
import com.kazakov.basic.bank.entity.Account;
import com.kazakov.basic.bank.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "accountName", source = "name")
    AccountDto toAccountCreationResult(Account account);

    @Mapping(target = "transferId", source = "id")
    TransferDto toTransferDto(Transfer transfer);

    TransferHistoryDto toTransferHistory(Long accountId, List<Transfer> transfers);
}
