package com.example.bankingApp.dto.CustomersDto;

import com.example.bankingApp.entity.Enums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomersRequestDto {

    @NotBlank(message = "Customer Name cannot be empty")
    private String customerName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    public CustomersRequestDto(String customerName, String email, String password, Roles roles) {
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public CustomersRequestDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
