package com.example.bankingApp.dto.AccountDto.Creation;

public class CreationRequestDto {

    private Long requestId;

    private String message;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
