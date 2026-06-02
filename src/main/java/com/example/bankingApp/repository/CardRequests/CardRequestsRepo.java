package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.CardRequests.CardType;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRequestsRepo extends JpaRepository<CardRequests,Long> {
    List<CardRequests> findByCustomersEmail(String email);

    @Query("""
       SELECT r FROM CardRequests r
       WHERE r.customers = :customer
       AND r.cardType = :cardType
       AND r.requestStatus = com.example.bankingApp.entity.Enums.RequestStatus.PENDING_REVIEW
       """)
    Optional<CardRequests> findPendingRequest(Customers customer,
                                              CardType cardType);

    long countByRequestStatus(RequestStatus requestStatus);
}
