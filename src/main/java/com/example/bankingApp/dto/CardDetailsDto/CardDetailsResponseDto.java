package com.example.bankingApp.dto.CardDetailsDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

public class CardDetailsResponseDto {

    private Long cardId;
    private Long customerId;
    private String customerName;

    private String maskedNumber;
    private String expiry;

    private Long cardTypeId;
    private String cardType;

    private Long cardVariantId;
    private String cardVariant;

    private Long networkId;
    private String networkName;
    private String binNumber;

    private String cardColourFront;
    private String cardColourBack;
    private String chipColour;
    private String textColour;

    private String createdAt;

    public CardDetailsResponseDto(CardDetails card) {
        this.cardId = card.getCardId();
        this.customerId = card.getCustomers().getCustomerId();
        this.customerName = card.getCustomers().getCustomerName();
        this.maskedNumber = card.getNetworkBin().getBinNumber() + "** **** **** " + card.getLast4();
        this.cardTypeId = card.getCardType().getTypeId();
        this.cardType = card.getCardType().getTypeName();
        this.cardVariantId = card.getCardVariant().getVariantId();
        this.cardVariant = card.getCardVariant().getVariantName();
        this.expiry = String.format("%02d/%02d", card.getExpiry().getMonthValue(),
                                                 card.getExpiry().getYear() % 100);
        this.networkId = card.getNetworkBin().getCardNetwork().getNetworkId();
        this.networkName = card.getNetworkBin().getCardNetwork().getNetworkName();
        this.binNumber =card.getNetworkBin().getBinNumber();
        this.cardColourFront = card.getCardVariant().getCardColourFront();
        this.cardColourBack = card.getCardVariant().getCardColourBack();
        this.chipColour = card.getCardVariant().getChipColour();
        this.textColour = card.getCardVariant().getTextColour();
        this.createdAt = card.getCreatedAt().toString();
    }

    public CardDetailsResponseDto() {
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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getCardVariantId() {
        return cardVariantId;
    }

    public void setCardVariantId(Long cardVariantId) {
        this.cardVariantId = cardVariantId;
    }

    public String getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(String cardVariant) {
        this.cardVariant = cardVariant;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}