package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.CardNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardNetworkRepo extends JpaRepository<CardNetwork,Long> {
}
