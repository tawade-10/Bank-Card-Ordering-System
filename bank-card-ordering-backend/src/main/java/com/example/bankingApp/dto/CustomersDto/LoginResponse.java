package com.example.bankingApp.dto.CustomersDto;

public class LoginResponse {

    private String token;
    private String customerName;
    private String email;

    public LoginResponse(String token, String customerName, String email) {
        this.token = token;
        this.customerName = customerName;
        this.email = email;
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
}
