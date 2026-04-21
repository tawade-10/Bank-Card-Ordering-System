package com.example.bankingApp.dto.CardDetailsDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CardDetailsRequestDto {

    @NotBlank
    private String cardNumber;

    @NotNull
    private Long requestId;

    @NotNull
    private Long cardType;

    @NotNull
    private Long cardVariant;

    @NotBlank
    private String expiry;

    @NotBlank
    private String cvv;

    public CardDetailsRequestDto() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(Long cardVariant) {
        this.cardVariant = cardVariant;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}