package com.example.bankingApp.repository.CardDetails;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardDetailsRepo extends JpaRepository<CardDetails, Long> {

    List<CardDetails> findByCustomersCustomerId(Long customerId);

    List<CardDetails> findByCustomers(Customers customers);

    @Modifying
    @Transactional
    @Query("""
    UPDATE CardDetails c SET c.isActive = false
    WHERE c.customers.customerId = :customerId
    AND c.cardType.typeName = :cardType
    """)
    void deactivatePreviousCard(Long customerId, String cardType);

    @Query("""
    SELECT c FROM CardDetails c
    WHERE c.customers.customerId = :customerId
    AND c.isActive = true
    ORDER BY c.createdAt DESC
    """)
    List<CardDetails> findActiveCards(Long customerId);

    @Query("""
    SELECT c FROM CardDetails c
    WHERE c.customers.customerId = :customerId
    AND c.cardType.typeName = :cardType
    AND c.isActive = true
    ORDER BY c.createdAt DESC
    """)
    List<CardDetails> findActiveCardsByType(Long customerId, String cardType);
}
