package com.example.bankingApp.repository.CardDetails;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.Customers.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardDetailsRepo extends JpaRepository<CardDetails, Long> {


    List<CardDetails> findByCustomersCustomerId(Long customerId);

    List<CardDetails> findByCustomers(Customers customers);
}
