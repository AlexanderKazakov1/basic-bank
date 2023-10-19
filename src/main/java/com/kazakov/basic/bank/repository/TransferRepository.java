package com.kazakov.basic.bank.repository;

import com.kazakov.basic.bank.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByFromAccountId(Long fromAccountId);
}
