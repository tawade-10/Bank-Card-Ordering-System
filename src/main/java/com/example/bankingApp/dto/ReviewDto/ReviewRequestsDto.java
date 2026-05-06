package com.example.bankingApp.dto.ReviewDto;

import com.example.bankingApp.entity.Enums.Status;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestsDto {

    @NotNull(message = "Status cannot be empty")
    private Status status;

    @NotNull(message = "Message cannot be empty")
    private String reviewMessage;

    public ReviewRequestsDto() {
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
}
