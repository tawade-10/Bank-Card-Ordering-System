package com.example.bankingApp.dto.CustomersDto;

import com.example.bankingApp.entity.Enums.Roles;

public class LoginResponse {

    private String token;
    private String customerName;
    private String email;
    private Roles roles;
    private Long userId;

    public LoginResponse(String token, String customerName, String email, Roles roles, Long userId) {
        this.token = token;
        this.customerName = customerName;
        this.email = email;
        this.roles = roles;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public Roles getRoles() { return roles; }

    public Long getUserId() { return userId; }
}
