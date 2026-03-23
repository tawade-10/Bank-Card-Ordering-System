package com.example.bankingApp.dto.CustomersDto;

import com.example.bankingApp.entity.Customers;

import java.time.LocalDate;
import java.time.LocalTime;

public class CustomersResponseDto {

    private Long customerId;

    private String customerName;

    private String email;

    private LocalDate createdDate;

    private LocalTime createdTime;

    public CustomersResponseDto(Long customerId, String customerName, String email, LocalDate createdDate, LocalTime createdTime) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    public CustomersResponseDto(Customers customers) {
        this.customerId = customers.getCustomerId();
        this.customerName = customers.getCustomerName();
        this.email = customers.getEmail();
        this.createdDate = customers.getCreatedDate();
        this.createdTime = customers.getCreatedTime();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }
}
