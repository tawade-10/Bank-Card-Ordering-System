package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepo extends JpaRepository<Bank,Long> {
}
