package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonForRequestRepo extends JpaRepository<Reason, Long> {
    Reason findByReasonName(String reason);
}
