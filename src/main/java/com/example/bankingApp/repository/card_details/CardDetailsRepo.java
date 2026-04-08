package com.example.bankingApp.repository.card_details;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDetailsRepo extends JpaRepository<CardDetails, Long> {



}
