package com.example.bankingApp.repository.Bank;

import com.example.bankingApp.entity.Bank.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch,Long> {
}
