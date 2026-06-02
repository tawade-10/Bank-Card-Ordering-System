package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRequestRepo extends JpaRepository<AccountRequest,Long> {
}
