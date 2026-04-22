package com.example.bankingApp.dto.CardDetailsDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

public class CardDetailsResponseDto {

    private Long cardId;
    private Long customerId;
    private String customerName;
    private String maskedNumber;
    private String cardTypeName;
    private String cardVariantName;
    private String expiry;
    private String cardColourFront;
    private String cardColourBack;
    private String chipColour;
    private String textColour;

    public CardDetailsResponseDto(CardDetails card) {
        this.cardId = card.getCardId();
        this.customerId = card.getCustomers().getCustomerId();
        this.customerName = card.getCustomers().getCustomerName();
        this.maskedNumber = "**** **** **** " + card.getLast4();
        this.cardTypeName = card.getCardType().getTypeName();
        this.cardVariantName = card.getCardVariant().getVariantName();
        this.expiry = String.format("%02d/%02d", card.getExpiry().getMonthValue(),
                                                 card.getExpiry().getYear() % 100);
        this.cardColourFront = card.getCardVariant().getCardColourFront();
        this.cardColourBack = card.getCardVariant().getCardColourBack();
        this.chipColour = card.getCardVariant().getChipColour();
        this.textColour = card.getCardVariant().getTextColour();
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

    public String getCardColourFront() {
        return cardColourFront;
    }

    public void setCardColourFront(String cardColourFront) {
        this.cardColourFront = cardColourFront;
    }

    public String getCardColourBack() {
        return cardColourBack;
    }

    public void setCardColourBack(String cardColourBack) {
        this.cardColourBack = cardColourBack;
    }

    public String getChipColour() {
        return chipColour;
    }

    public void setChipColour(String chipColour) {
        this.chipColour = chipColour;
    }

    public String getTextColour() {
        return textColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
    }
}