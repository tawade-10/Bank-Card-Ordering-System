package com.example.bankingApp.dto.ReviewDto;

import com.example.bankingApp.entity.Enums.RequestStatus;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestsDto {

    @NotNull(message = "Status cannot be empty")
    private RequestStatus requestStatus;

    @NotNull(message = "Message cannot be empty")
    private String reviewMessage;

    public ReviewRequestsDto() {
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
}
