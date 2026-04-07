package com.example.bankingApp.dto.CardRequestsDto;

import jakarta.validation.constraints.NotNull;

public class CardRequestsDto {

    @NotNull(message = "Card Type ID is required")
    private Long cardTypeId;

    @NotNull(message = "Card Variant ID is required")
    private Long cardVariantId;

    @NotNull(message = "Reason ID is required")
    private Long reasonId;

    public CardRequestsDto() {}

    public Long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public Long getCardVariantId() {
        return cardVariantId;
    }

    public void setCardVariantId(Long cardVariantId) {
        this.cardVariantId = cardVariantId;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }
}