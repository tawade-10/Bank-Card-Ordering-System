package com.example.bankingApp.dto.RequestCardDto;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.entity.RequestNewCard.RequestNewCard;

public class ResponseDto {

    private Long requestId;
    private String cardType;
    private String cardVariant;
    private String reason;
    private Status status;
    private String localDate;
    private Customers customers;

    public ResponseDto(RequestNewCard requestNewCard) {
        this.requestId = requestNewCard.getRequestId();
        this.cardType = requestNewCard.getCardType().getTypeName();
        this.cardVariant = requestNewCard.getCardVariant().getVariantName();
        this.reason = requestNewCard.getReason().getReasonName();
        this.status = requestNewCard.getStatus();
        this.localDate = requestNewCard.getLocalDate().toString();
        this.customers = requestNewCard.getCustomers();
    }

    public ResponseDto() {}

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(String cardVariant) {
        this.cardVariant = cardVariant;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}