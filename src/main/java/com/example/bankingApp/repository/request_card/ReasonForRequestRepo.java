package com.example.bankingApp.repository.request_card;

import com.example.bankingApp.entity.RequestNewCard.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonForRequestRepo extends JpaRepository<Reason, Long> {
    Reason findByReasonName(String reason);
}
