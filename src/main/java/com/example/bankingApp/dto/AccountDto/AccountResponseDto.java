package com.example.bankingApp.dto.AccountDto;

import com.example.bankingApp.entity.Customers.Account;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccountResponseDto {

    private Long accountId;

    private String accountType;

    private String status;

    private LocalDate createdDate;

    private LocalTime createdTime;

    private String purpose;

    public AccountResponseDto(Account account){
        this.accountId = account.getAccountId();
        this.accountType = account.getAccountType().getTypeName();
        this.status = account.getStatus();
        this.createdDate = account.getCreatedDate();
        this.createdTime = account.getCreatedTime();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
