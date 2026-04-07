package com.example.bankingApp.repository.card;

import com.example.bankingApp.entity.request_card.ReasonForRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonForRequestRepo extends JpaRepository<ReasonForRequest, Long> {
    ReasonForRequest findByReasonName(String reason);
}
