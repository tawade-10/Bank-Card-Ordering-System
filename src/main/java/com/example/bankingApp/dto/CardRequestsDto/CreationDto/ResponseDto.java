package com.example.bankingApp.dto.CardRequestsDto.CreationDto;

import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.Enums.RequestStatus;
import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder({
        "cardRequestId",
        "cardTypeId",
        "cardType",
        "cardVariantId",
        "cardVariant",
        "cardNetworkId",
        "cardNetwork",
        "reason",
        "requestStatus",
        "customerId",
        "customerName",
        "createdDate",
        "createdTime",
        "message"
})
public class ResponseDto {

    private Long cardRequestId;
    private Long cardTypeId;
    private String cardType;
    private Long cardVariantId;
    private String cardVariant;
    private Long cardNetworkId;
    private String cardNetwork;
    private String reason;
    private RequestStatus requestStatus;
    private Long customerId;
    private String customerName;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String message;

    public ResponseDto(CardRequests cardRequests) {
        this.cardRequestId = cardRequests.getRequestId();
        this.cardTypeId = cardRequests.getCardType().getTypeId();
        this.cardType = cardRequests.getCardType().getTypeName();
        this.cardVariantId = cardRequests.getCardVariant().getVariantId();
        this.cardVariant = cardRequests.getCardVariant().getVariantName();
        this.cardNetworkId = cardRequests.getCardNetwork().getNetworkId();
        this.cardNetwork = cardRequests.getCardNetwork().getNetworkName();
        this.reason = cardRequests.getReason().getReasonName();
        this.requestStatus = cardRequests.getRequestStatus();
        this.customerId = cardRequests.getCustomers().getCustomerId();
        this.customerName = cardRequests.getCustomers().getCustomerName();
        this.createdDate = cardRequests.getCreatedDate();
        this.createdTime = cardRequests.getCreatedTime();
    }

    public ResponseDto(NotificationsResponseDto notificationsResponseDto){
        this.message = notificationsResponseDto.getMessage();
    }

    public ResponseDto() {}

    public Long getCardRequestId() { return cardRequestId; }

    public void setCardRequestId(Long cardRequestId) { this.cardRequestId = cardRequestId; }

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

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}