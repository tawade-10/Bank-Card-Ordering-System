package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.CardVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardVariantRepo extends JpaRepository<CardVariant,Long> {
    CardVariant findByVariantName(String cardVariant);
}
