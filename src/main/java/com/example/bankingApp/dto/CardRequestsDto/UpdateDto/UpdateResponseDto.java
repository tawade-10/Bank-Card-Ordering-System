package com.example.bankingApp.dto.CardRequestsDto.UpdateDto;

import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Enums.Status;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder({
        "requestId",
        "cardTypeId",
        "cardType",
        "cardVariantId",
        "cardVariant",
        "cardNetworkId",
        "cardNetwork",
        "reason",
        "status",
        "localDate",
        "customerId",
        "customerName",
        "updatedDate",
        "updatedTime"
})
public class UpdateResponseDto {

    private Long requestId;
    private Long cardTypeId;
    private String cardType;
    private Long cardVariantId;
    private String cardVariant;
    private Long cardNetworkId;
    private String cardNetwork;
    private String reason;
    private Status status;
    private String localDate;
    private Long customerId;
    private String customerName;
    private LocalDate updatedDate;
    private LocalTime updatedTime;

    public UpdateResponseDto(CardRequests cardRequests) {
        this.requestId = cardRequests.getRequestId();
        this.cardTypeId = cardRequests.getCardType().getTypeId();
        this.cardType = cardRequests.getCardType().getTypeName();
        this.cardVariantId = cardRequests.getCardVariant().getVariantId();
        this.cardVariant = cardRequests.getCardVariant().getVariantName();
        this.cardNetworkId = cardRequests.getCardNetwork().getNetworkId();
        this.cardNetwork = cardRequests.getCardNetwork().getNetworkName();
        this.reason = cardRequests.getReason().getReasonName();
        this.status = cardRequests.getStatus();
        this.localDate = cardRequests.getLocalDate().toString();
        this.customerId = cardRequests.getCustomers().getCustomerId();
        this.customerName = cardRequests.getCustomers().getCustomerName();
        this.updatedDate = cardRequests.getUpdatedDate();
        this.updatedTime = cardRequests.getUpdatedTime();
    }

    public UpdateResponseDto() {}

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

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
