package com.example.bankingApp.repository.Token;

import com.example.bankingApp.entity.Token.PasswordResetToken;
import com.example.bankingApp.entity.Customers.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    void deleteByCustomer(Customers customer);
}