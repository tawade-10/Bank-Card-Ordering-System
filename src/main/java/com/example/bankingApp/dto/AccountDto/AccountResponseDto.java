package com.example.bankingApp.dto.AccountDto;

import com.example.bankingApp.entity.Bank.AccountCreation;

import java.time.LocalDateTime;

public class AccountResponseDto {

    private Long accountId;
    private String customerName;
    private String accountNumber;
    private String accountType;
    private String branchName;
    private String ifscCode;
    private String status;
    private Double balance;
    private LocalDateTime openedAt;
    private LocalDateTime updatedAt;
    private String message;

    public AccountResponseDto(AccountCreation accountCreation){
        this.accountId = accountCreation.getAccountId();
        this.accountNumber = accountCreation.getAccountNumber();
        this.customerName = accountCreation.getCustomer().getCustomerName();
        this.accountType = accountCreation.getAccountType().getTypeName();
        this.branchName= accountCreation.getBranch().getBranchName();
        this.ifscCode = accountCreation.getBranch().getIfscCode();
        this.balance = accountCreation.getBalance();
        this.status = accountCreation.getStatus().toString();
        this.openedAt = accountCreation.getOpenedAt();
        this.updatedAt = accountCreation.getUpdatedAt();
        this.message = accountCreation.getMessage();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
