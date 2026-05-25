package com.example.bankingApp.dto.CustomersDto.UpdateDto;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder({
        "customerId",
        "customerName",
        "email",
        "roles",
        "updatedDate",
        "updatedTime",
})
public class CustomersUpdateResponseDto {

    private Long customerId;

    private String customerName;

    private String email;

    private Roles roles;

    private LocalDate updatedDate;

    private LocalTime updatedTime;

    public CustomersUpdateResponseDto(Customers customers) {
        this.customerId = customers.getCustomerId();
        this.customerName = customers.getCustomerName();
        this.email = customers.getEmail();
        this.roles = customers.getRoles();
        this.updatedDate = customers.getUpdatedDate();
        this.updatedTime = customers.getUpdatedTime();
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

    public LocalDate getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(LocalDate updatedDate) { this.updatedDate = updatedDate; }

    public LocalTime getUpdatedTime() { return updatedTime; }

    public void setUpdatedTime(LocalTime updatedTime) { this.updatedTime = updatedTime; }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
