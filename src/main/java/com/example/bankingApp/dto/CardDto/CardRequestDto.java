package com.example.bankingApp.dto.CardDto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class CardRequestDto {

    @NotNull(message = "Card Number cannot be empty")
    private Long cardNumber;

    @NotNull(message = "Card Type is required")
    private Long cardType;

    @NotNull(message = "Card Variant is required")
    private Long cardVariant;

    @NotNull(message = "Expiry Date is required")
    private Date expiryDate;

    public CardRequestDto() {}

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}