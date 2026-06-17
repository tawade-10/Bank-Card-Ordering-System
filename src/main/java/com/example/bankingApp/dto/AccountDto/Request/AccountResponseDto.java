package com.example.bankingApp.dto.AccountDto.Request;

import com.example.bankingApp.entity.Bank.AccountRequest;
import com.example.bankingApp.entity.Enums.AccountStatus;

import java.time.LocalDateTime;

public class AccountResponseDto {

    private Long accountRequestId;
    private Long customerId;
    private String customerName;
    private Long bankId;
    private String bankName;
    private Long branchId;
    private String branchName;
    private Long accountTypeId;
    private String accountType;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

    public AccountResponseDto(AccountRequest accountRequest){
        this.accountRequestId = accountRequest.getRequestId();
        this.customerId = accountRequest.getCustomer().getCustomerId();
        this.customerName = accountRequest.getCustomer().getCustomerName();
        this.bankId = accountRequest.getBranch().getBank().getBankId();
        this.bankName = accountRequest.getBranch().getBank().getBankName();
        this.branchId = accountRequest.getBranch().getBranchId();
        this.branchName = accountRequest.getBranch().getBranchName();
        this.accountTypeId = accountRequest.getAccountType().getTypeId();
        this.accountType = accountRequest.getAccountType().getTypeName();
        this.accountStatus = accountRequest.getStatus();
        this.createdAt = accountRequest.getCreatedAt();
        this.updatedAt = accountRequest.getUpdatedAt();
        this.message = accountRequest.getMessage();
    }

    public Long getAccountRequestId() { return accountRequestId; }

    public void setAccountRequestId(Long accountRequestId) { this.accountRequestId = accountRequestId; }

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

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
