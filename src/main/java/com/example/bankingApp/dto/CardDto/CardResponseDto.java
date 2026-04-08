package com.example.bankingApp.dto.CardDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

import java.util.Date;

public class CardResponseDto {

    private Long cardId;

    private Long customerId;

    private Long cardNumber;

    private Long cardType;

    private Long cardVariant;

    private Date expiryDate;

    public CardResponseDto(CardDetails cardDetails) {
        this.cardId = cardDetails.getCardId();
        this.customerId = cardDetails.getCustomers().getCustomerId();
        this.cardNumber = cardDetails.getCardNumber();
        this.cardType = cardDetails.getCardType().getTypeId();
        this.cardVariant = cardDetails.getCardVariant().getVariantId();
        this.expiryDate = cardDetails.getExpiryDate();
    }

    public CardResponseDto(){
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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
