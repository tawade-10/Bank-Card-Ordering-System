package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepo extends JpaRepository<CardType,Long> {
    CardType findByTypeName(String cardType);
}
