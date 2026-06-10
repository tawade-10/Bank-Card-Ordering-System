package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.Account;
import com.example.bankingApp.entity.Customers.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountCreationRepo extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByCustomer(Customers customer);

    List<Account> findByCustomer(Customers customer);
}
