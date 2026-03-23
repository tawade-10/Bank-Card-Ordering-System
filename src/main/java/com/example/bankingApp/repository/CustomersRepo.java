package com.example.bankingApp.repository;

import com.example.bankingApp.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepo extends JpaRepository<Customers,Long> {

    UserDetails findByEmail(String email);
}
