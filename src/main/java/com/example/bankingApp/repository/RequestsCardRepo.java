package com.example.bankingApp.repository;

import com.example.bankingApp.entity.RequestCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsCardRepo extends JpaRepository<RequestCard,Long> {
}
