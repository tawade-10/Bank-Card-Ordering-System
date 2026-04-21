package com.example.bankingApp.dto.MessageDto;

import com.example.bankingApp.entity.Enums.Status;

public class MessageRequestDto {

    private Status status;

    private String reviewMessage;

    public MessageRequestDto() {
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
