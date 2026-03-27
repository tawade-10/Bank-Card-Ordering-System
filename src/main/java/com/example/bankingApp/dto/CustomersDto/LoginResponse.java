package com.example.bankingApp.dto.CustomersDto;

public class LoginResponse {

    private String token;
    private String customerName;
    private String email;
    private String role;

    public LoginResponse(String token, String customerName, String email, String role) {
        this.token = token;
        this.customerName = customerName;
        this.email = email;
        this.role = role;
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

    public String getRole() {return role; }
}
