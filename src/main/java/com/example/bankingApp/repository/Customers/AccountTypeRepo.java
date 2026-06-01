package com.example.bankingApp.repository.Customers;

import com.example.bankingApp.entity.Customers.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepo extends JpaRepository<AccountType,Long> {
}
