package com.example.bankingApp.dto.ReviewDto;

import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Enums.Status;
import jakarta.validation.constraints.NotNull;

public class ReviewResponseDto {

    private Long requestId;

    private Status status;

    private String reviewMessage;

    private String updatedAt;

    public ReviewResponseDto(CardRequests cardRequests) {
        this.requestId = cardRequests.getRequestId();
        this.status = cardRequests.getStatus();
        this.reviewMessage = cardRequests.getReviewMessage();
        this.updatedAt = cardRequests.getUpdatedAt().toString();
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
