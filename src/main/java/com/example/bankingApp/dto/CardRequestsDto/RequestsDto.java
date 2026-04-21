package com.example.bankingApp.dto.CardRequestsDto;

import com.example.bankingApp.entity.Enums.Status;
import jakarta.validation.constraints.NotNull;

public class RequestsDto {

    @NotNull(message = "Card Type ID is required")
    private Long cardTypeId;
    @NotNull(message = "Card Variant ID is required")
    private Long cardVariantId;
    @NotNull(message = "Reason ID is required")
    private Long reasonId;
    private Status status;
    private String reviewMessage;

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

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}