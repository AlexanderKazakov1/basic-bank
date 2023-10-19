package com.kazakov.basic.bank.repository;

import com.kazakov.basic.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
