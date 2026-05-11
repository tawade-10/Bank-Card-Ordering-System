package com.example.bankingApp.dto.ActiveCardsDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

public class ActiveCardsResponseDto {

    private Long cardId;
    private Long customerId;
    private String customerName;

    private String maskedNumber;
    private String expiry;

    private String cardType;
    private String networkName;
    private String binNumber;

    private Long cardVariantId;
    private String cardVariant;

    public ActiveCardsResponseDto(CardDetails cardDetails) {
        this.cardId = cardDetails.getCardId();
        this.customerId = cardDetails.getCustomers().getCustomerId();
        this.customerName = cardDetails.getCustomers().getCustomerName();
        this.maskedNumber = cardDetails.getNetworkBin().getBinNumber() + "** **** **** " + cardDetails.getLast4();
        this.expiry = String.format("%02d/%02d", cardDetails.getExpiry().getMonthValue(),
                cardDetails.getExpiry().getYear() % 100);
        this.cardType = cardDetails.getCardType().getTypeName();
        this.networkName = cardDetails.getNetworkBin().getCardNetwork().getNetworkName();
        this.binNumber =cardDetails.getNetworkBin().getBinNumber();
        this.cardVariantId = cardDetails.getCardVariant().getVariantId();
        this.cardVariant = cardDetails.getCardVariant().getVariantName();
    }

    public ActiveCardsResponseDto() {
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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
}
