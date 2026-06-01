package com.example.bankingApp.repository.Customers;

import com.example.bankingApp.entity.Customers.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
