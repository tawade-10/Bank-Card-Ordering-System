package com.example.bankingApp.dto.CardRequestsDto;

import com.example.bankingApp.entity.RequestCard;
import jakarta.validation.constraints.NotBlank;

public class CardResponseDto {

    private Long requestId;

    private String cardType;

    private String cardVariant;

    private String reason;

    public CardResponseDto(RequestCard requestCard) {
        this.requestId = requestCard.getRequestId();
        this.cardType = requestCard.getCardType();
        this.cardVariant = requestCard.getCardVariant();
        this.reason = requestCard.getReason();
    }

    public CardResponseDto() {
    }

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
}
