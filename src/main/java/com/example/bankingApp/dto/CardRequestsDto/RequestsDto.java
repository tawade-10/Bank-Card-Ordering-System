package com.example.bankingApp.dto.CardRequestsDto;

import jakarta.validation.constraints.NotNull;

public class RequestsDto {

    @NotNull(message = "Card Type ID is required")
    private Long cardTypeId;
    @NotNull(message = "Card Variant ID is required")
    private Long cardVariantId;
    @NotNull(message = "Network ID is required")
    private Long cardNetworkId;
    @NotNull(message = "Reason ID is required")
    private Long reasonId;

    public RequestsDto() {}

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

    public Long getCardNetworkId() {
        return cardNetworkId;
    }

    public void setCardNetworkId(Long cardNetworkId) {
        this.cardNetworkId = cardNetworkId;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }
}