package com.example.bankingApp.dto.MessageDto;

import com.example.bankingApp.entity.Enums.Status;

public class MessageResponseDto {

    private Status status;

    private String reviewMessage;

    private String message;

    public MessageResponseDto(Status status, String reviewMessage, String message) {
        this.status = status;
        this.reviewMessage = reviewMessage;
        this.message = message;
    }

    public MessageResponseDto() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
