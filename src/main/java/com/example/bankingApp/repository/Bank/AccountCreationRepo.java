package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.AccountCreation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCreationRepo extends JpaRepository<AccountCreation, Long> {
}
