package com.example.bankingApp.dto.ReviewDto;

import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Enums.Status;
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

    private Status status;

    private String reviewMessage;

    private LocalDate updatedDate;

    private LocalTime updatedTime;

    public ReviewResponseDto(CardRequests cardRequests) {
        this.requestId = cardRequests.getRequestId();
        this.status = cardRequests.getStatus();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
