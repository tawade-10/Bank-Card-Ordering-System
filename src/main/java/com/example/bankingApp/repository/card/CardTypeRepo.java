package com.example.bankingApp.repository.card;

import com.example.bankingApp.entity.request_card.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepo extends JpaRepository<CardType,Long> {
    CardType findByTypeName(String cardType);
}
