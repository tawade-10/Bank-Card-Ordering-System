package com.example.bankingApp.dto.AccountDto.Creation;

import com.example.bankingApp.entity.Bank.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreationResponseDto {

    private Long accountId;
    private String customerName;
    private String accountNumber;
    private String accountType;
    private String branchName;
    private String ifscCode;
    private String status;
    private BigDecimal balance;
    private LocalDate dateOpened;
    private LocalTime timeOpened;
    private LocalDate dateUpdated;
    private LocalTime timeUpdated;
    private String message;

    public CreationResponseDto(Account account) {
        this.accountId = account.getAccountId();
        this.customerName = account.getCustomer().getCustomerName();
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType().getTypeName();
        this.branchName = account.getBranch().getBranchName();
        this.ifscCode = account.getBranch().getIfscCode();
        this.status = account.getAccountStatus().toString();
        this.balance = account.getBalance();
        this.dateOpened = account.getCreatedDate();
        this.timeOpened = account.getCreatedTime();
        this.dateUpdated = account.getUpdatedDate();
        this.timeUpdated = account.getUpdatedTime();
        this.message = account.getMessage();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public LocalTime getTimeOpened() {
        return timeOpened;
    }

    public void setTimeOpened(LocalTime timeOpened) {
        this.timeOpened = timeOpened;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

