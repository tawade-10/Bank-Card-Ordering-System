package com.example.bankingApp.dto.CustomersDto;

import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.entity.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomersRequestDto {

    @NotBlank(message = "Customer Name cannot be empty")
    private String customerName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    private Roles roles;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public CustomersRequestDto(Customers customers) {
        this.customerName = customers.getCustomerName();
        this.email = customers.getEmail();
        this.roles = customers.getRoles();
        this.password = customers.getPassword();
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
