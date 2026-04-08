package com.example.bankingApp.repository.request_card;

import com.example.bankingApp.entity.RequestNewCard.CardVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardVariantRepo extends JpaRepository<CardVariant,Long> {
    CardVariant findByVariantName(String cardVariant);
}
