package com.example.bankingApp.dto.Notifications;

public class NotificationsRequestDto {

    private Long customerId;
    private String title;
    private String message;
    private String type;
    private Long referenceId;

    public NotificationsRequestDto(Long customerId, String title, String message, String type, Long referenceId) {
        this.customerId = customerId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.referenceId = referenceId;
    }

    public NotificationsRequestDto() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
}
