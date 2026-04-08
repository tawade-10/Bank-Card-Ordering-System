package com.example.bankingApp.dto.RequestCardDto;

import com.example.bankingApp.entity.RequestNewCard.RequestNewCard;

public class ResponseDto {

    private Long requestId;
    private String cardType;
    private String cardVariant;
    private String reason;
    private String statusOfRequest;
    private String localDate;

    public ResponseDto(RequestNewCard requestNewCard) {
        this.requestId = requestNewCard.getRequestId();
        this.cardType = requestNewCard.getCardType().getTypeName();
        this.cardVariant = requestNewCard.getCardVariant().getVariantName();
        this.reason = requestNewCard.getReason().getReasonName();
        this.statusOfRequest = requestNewCard.getStatusOfRequest().name();
        this.localDate = requestNewCard.getLocalDate().toString();
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

    public String getStatusOfRequest() {
        return statusOfRequest;
    }

    public void setStatusOfRequest(String statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }
}