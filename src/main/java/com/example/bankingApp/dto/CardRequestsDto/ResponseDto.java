package com.example.bankingApp.dto.CardRequestsDto;

import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.entity.CardRequests.CardRequests;

public class ResponseDto {

    private Long requestId;
    private Long cardTypeId;
    private String cardType;
    private Long cardVariantId;
    private String cardVariant;
    private Long cardNetworkId;
    private String cardNetwork;
    private String bin;
    private String cardColourFront;
    private String cardColourBack;
    private String chipColour;
    private String textColour;
    private String reason;
    private Status status;
    private String localDate;
    private Long customerId;
    private String customerName;
    private String reviewMessage;
    private String updatedAt;

    public ResponseDto(CardRequests cardRequests) {
        this.requestId = cardRequests.getRequestId();
        this.cardTypeId = cardRequests.getCardType().getTypeId();
        this.cardType = cardRequests.getCardType().getTypeName();
        this.cardVariantId = cardRequests.getCardVariant().getVariantId();
        this.cardVariant = cardRequests.getCardVariant().getVariantName();
        this.cardNetworkId = cardRequests.getCardNetwork().getNetworkId();
        this.cardNetwork = cardRequests.getCardNetwork().getNetworkName();
        this.bin = cardRequests.getNetworkBin().getBinNumber();
        this.cardColourFront = cardRequests.getCardVariant().getCardColourFront();
        this.cardColourBack = cardRequests.getCardVariant().getCardColourBack();
        this.chipColour = cardRequests.getCardVariant().getChipColour();
        this.textColour = cardRequests.getCardVariant().getTextColour();
        this.reason = cardRequests.getReason().getReasonName();
        this.status = cardRequests.getStatus();
        this.localDate = cardRequests.getLocalDate().toString();
        this.customerId = cardRequests.getCustomers().getCustomerId();
        this.customerName = cardRequests.getCustomers().getCustomerName();
        this.reviewMessage = cardRequests.getReviewMessage();
        this.updatedAt = cardRequests.getUpdatedAt().toString();
    }

    public ResponseDto() {}

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public Long getCardNetworkId() {
        return cardNetworkId;
    }

    public void setCardNetworkId(Long cardNetworkId) {
        this.cardNetworkId = cardNetworkId;
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
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

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}