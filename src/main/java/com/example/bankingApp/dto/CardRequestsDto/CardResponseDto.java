package com.example.bankingApp.dto.CardRequestsDto;

import com.example.bankingApp.entity.request_card.RequestCard;

public class CardResponseDto {

    private Long requestId;
    private String cardType;
    private String cardVariant;
    private String reason;
    private String statusOfRequest;
    private String localDate;

    public CardResponseDto(RequestCard requestCard) {
        this.requestId = requestCard.getRequestId();
        this.cardType = requestCard.getCardType().getTypeName();
        this.cardVariant = requestCard.getCardVariant().getVariantName();
        this.reason = requestCard.getReason().getReasonName();
        this.statusOfRequest = requestCard.getStatusOfRequest().name();
        this.localDate = requestCard.getLocalDate().toString();
    }

    public CardResponseDto() {}

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