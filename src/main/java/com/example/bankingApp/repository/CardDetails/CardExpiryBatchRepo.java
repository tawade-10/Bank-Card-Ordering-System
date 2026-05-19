package com.example.bankingApp.repository.CardDetails;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardDetails.CardExpiryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface CardExpiryBatchRepo extends JpaRepository<CardExpiryBatch, Long> {

    @Query("SELECT c FROM CardDetails c WHERE c.expiry = :expiry AND c.isActive = true")
    List<CardDetails> findExpiringCards(YearMonth expiry);
}
