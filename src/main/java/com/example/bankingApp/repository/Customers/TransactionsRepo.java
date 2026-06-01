package com.example.bankingApp.repository.Customers;

import com.example.bankingApp.entity.Customers.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {
}
