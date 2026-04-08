package com.example.bankingApp.repository.request_card;

import com.example.bankingApp.entity.RequestNewCard.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepo extends JpaRepository<CardType,Long> {
    CardType findByTypeName(String cardType);
}
