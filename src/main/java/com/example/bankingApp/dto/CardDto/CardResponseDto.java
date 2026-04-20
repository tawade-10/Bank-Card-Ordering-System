package com.example.bankingApp.dto.CardDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

public class CardResponseDto {

    private Long cardId;
    private Long customerId;
    private String customerName;
    private String maskedNumber;
    private String cardTypeName;
    private String cardVariantName;
    private String expiry;

    public CardResponseDto(CardDetails card) {
        this.cardId = card.getCardId();
        this.customerId = card.getCustomers().getCustomerId();
        this.customerName = card.getCustomers().getCustomerName();
        this.maskedNumber = "**** **** **** " + card.getLast4();
        this.cardTypeName = card.getCardType().getTypeName();
        this.cardVariantName = card.getCardVariant().getVariantName();
        this.expiry = String.format("%02d/%02d", card.getExpiry().getMonthValue(),
                                                 card.getExpiry().getYear() % 100);
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardVariantName() {
        return cardVariantName;
    }

    public void setCardVariantName(String cardVariantName) {
        this.cardVariantName = cardVariantName;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}