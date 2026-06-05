package com.example.bankingApp.service;

import com.example.bankingApp.entity.Bank.Branch;
import org.springframework.stereotype.Service;

@Service
public class AccountNumberGenerator {

    public String generate(Branch branch) {

        String accountNumber = branch.getBranchCode() + String.format("%08d",
                branch.getNextAccountSequence());
        branch.setNextAccountSequence(branch.getNextAccountSequence() + 1);
        return accountNumber;
    }
}
