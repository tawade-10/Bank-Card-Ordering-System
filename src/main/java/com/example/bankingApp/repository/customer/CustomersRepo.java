package com.example.bankingApp.repository.customer;

import com.example.bankingApp.entity.Customers.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepo extends JpaRepository<Customers, Long> {

    Optional<Customers> findByEmail(String email);

    boolean existsByEmail(String mail);
}