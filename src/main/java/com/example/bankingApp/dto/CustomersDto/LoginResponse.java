package com.example.bankingApp.dto.CustomersDto;

import com.example.bankingApp.entity.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "token",
        "customerName",
        "email",
        "roles",
        "userId",
        "message"
})
public class LoginResponse {

    private String token;
    private String customerName;
    private String email;
    private Roles roles;
    private Long userId;
    private String message;

    public LoginResponse(String token, String customerName, String email, Roles roles, Long userId, String message) {
        this.token = token;
        this.customerName = customerName;
        this.email = email;
        this.roles = roles;
        this.userId = userId;
        this.message = message;
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

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
