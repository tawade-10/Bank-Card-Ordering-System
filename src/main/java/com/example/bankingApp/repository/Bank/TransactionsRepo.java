package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {
}
