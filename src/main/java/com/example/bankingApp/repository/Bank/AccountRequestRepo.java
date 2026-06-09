package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.AccountRequest;
import com.example.bankingApp.entity.Enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRequestRepo extends JpaRepository<AccountRequest,Long> {

    List<AccountRequest> findByStatus(AccountStatus status);
}
