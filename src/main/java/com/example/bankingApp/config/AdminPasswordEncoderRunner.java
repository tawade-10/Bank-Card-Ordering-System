package com.example.bankingApp.config;

import com.example.bankingApp.repository.customer.CustomersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminPasswordEncoderRunner implements CommandLineRunner {

    @Autowired
    private CustomersRepo customersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        customersRepo.findByEmail("admin@bank.com").ifPresent(admin -> {
            String rawPassword = admin.getPassword();

            if (!rawPassword.startsWith("$2a$")) {
                admin.setPassword(passwordEncoder.encode(rawPassword));
                customersRepo.save(admin);
//                System.out.println("Admin password encoded successfully!");
            }
        });
    }
}