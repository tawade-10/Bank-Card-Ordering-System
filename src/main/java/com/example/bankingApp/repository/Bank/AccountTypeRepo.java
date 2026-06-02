package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepo extends JpaRepository<AccountType,Long> {
}
