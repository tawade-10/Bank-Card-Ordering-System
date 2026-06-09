package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCreationRepo extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);

}
