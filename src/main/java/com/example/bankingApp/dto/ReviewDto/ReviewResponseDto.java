package com.example.bankingApp.dto.ReviewDto;

import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder({
        "requestId",
        "status",
        "reviewMessage",
        "updatedDate",
        "updatedTime"
})
public class ReviewResponseDto {

    private Long requestId;

    private RequestStatus requestStatus;

    private String reviewMessage;

    private LocalDate updatedDate;

    private LocalTime updatedTime;

    public ReviewResponseDto(CardRequests cardRequests) {
        this.requestId = cardRequests.getRequestId();
        this.requestStatus = cardRequests.getRequestStatus();
        this.reviewMessage = cardRequests.getReviewMessage();
        this.updatedDate = cardRequests.getUpdatedDate();
        this.updatedTime = cardRequests.getUpdatedTime();
    }

    public ReviewResponseDto() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public RequestStatus getStatus() {
        return requestStatus;
    }

    public void setStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
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
