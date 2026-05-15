package com.example.bankingApp.dto;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryNotificationsDto {

    private String cardNumber;
    private YearMonth expiryDate;
    private String customerName;

    public ExpiryNotificationsDto(String cardNumber, YearMonth expiryDate, String customerName) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.customerName = customerName;
    }

    public ExpiryNotificationsDto() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}