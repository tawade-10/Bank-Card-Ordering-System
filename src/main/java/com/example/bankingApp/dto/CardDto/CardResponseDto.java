package com.example.bankingApp.dto.CardDto;

import com.example.bankingApp.entity.CardDetails.CardDetails;

import java.util.Date;

public class CardResponseDto {

    private Long cardId;
    private Long customerId;

    private String cardNumber;     // encrypted
    private String maskedNumber;   // NEW → for frontend

    private Long cardType;         // ID
    private String cardTypeName;   // NEW → for frontend

    private Long cardVariant;      // ID
    private String cardVariantName;// NEW → for frontend

    private Date expiryDate;       // original
    private String expiry;         // NEW → simple MM/YYYY

    public CardResponseDto(CardDetails cardDetails) {

        this.cardId = cardDetails.getCardId();
        this.customerId = cardDetails.getCustomers().getCustomerId();

        // encrypted saved number
        this.cardNumber = cardDetails.getCardNumber();

        // NEW → only return last 4 digits (SAFE)
        String decrypted = cardDetails.getCardNumber();   // if using decryption service, apply it
        this.maskedNumber = "**** **** **** " + decrypted.substring(decrypted.length() - 4);

        // keep ID
        this.cardType = cardDetails.getCardType().getTypeId();
        // NEW readable name
        this.cardTypeName = cardDetails.getCardType().getTypeName();

        this.cardVariant = cardDetails.getCardVariant().getVariantId();
        this.cardVariantName = cardDetails.getCardVariant().getVariantName();

        // original
        this.expiryDate = cardDetails.getExpiryDate();

        // NEW → super simple expiry format
        this.expiry = (cardDetails.getExpiryDate().getMonth() + 1)
                + "/" +
                (cardDetails.getExpiryDate().getYear() + 1900);
    }

    public CardResponseDto() {}

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Long getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(Long cardVariant) {
        this.cardVariant = cardVariant;
    }

    public String getCardVariantName() {
        return cardVariantName;
    }

    public void setCardVariantName(String cardVariantName) {
        this.cardVariantName = cardVariantName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}