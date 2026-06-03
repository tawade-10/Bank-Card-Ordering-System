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

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
