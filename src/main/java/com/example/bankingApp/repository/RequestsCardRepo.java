package com.example.bankingApp.repository;

import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.entity.RequestCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestsCardRepo extends JpaRepository<RequestCard,Long> {

    List<RequestCard> findByCustomerEmail(String email);
}
