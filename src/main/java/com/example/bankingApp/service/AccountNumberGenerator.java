package com.example.bankingApp.service;

import com.example.bankingApp.entity.Bank.Branch;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountNumberGenerator {

    public String generate() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000000L,
                                                                   9999999999999999L)
        );
    }
}