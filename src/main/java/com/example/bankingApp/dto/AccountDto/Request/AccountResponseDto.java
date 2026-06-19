package com.example.bankingApp.dto.AccountDto.Request;

import com.example.bankingApp.entity.Bank.AccountRequest;
import com.example.bankingApp.entity.Enums.AccountStatus;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate createdDate;
    private LocalTime createdTime;
    private LocalDate updatedDate;
    private LocalTime updatedTime;
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
        this.createdDate = accountRequest.getCreatedDate();
        this.createdTime = accountRequest.getCreatedTime();
        this.updatedDate = accountRequest.getUpdatedDate();
        this.updatedTime = accountRequest.getUpdatedTime();
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

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
