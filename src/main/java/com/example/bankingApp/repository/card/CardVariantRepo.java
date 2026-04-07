package com.example.bankingApp.repository.card;

import com.example.bankingApp.entity.request_card.CardVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardVariantRepo extends JpaRepository<CardVariant,Long> {
    CardVariant findByVariantName(String cardVariant);
}
