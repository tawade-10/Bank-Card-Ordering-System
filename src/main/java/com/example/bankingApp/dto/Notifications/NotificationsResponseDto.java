package com.example.bankingApp.dto.Notifications;

import com.example.bankingApp.entity.Notification.Notifications;

import java.time.LocalDateTime;

public class NotificationsResponseDto {

    private Long notificationId;
    private String title;
    private String message;
    private String type;
    private Long referenceId;
    private boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotificationsResponseDto(Notifications notifications) {
        this.notificationId = notifications.getNotificationid();
        this.title = notifications.getTitle();
        this.message = notifications.getMessage();
        this.type = notifications.getType();
        this.referenceId = notifications.getReferenceId();
        this.read = notifications.isRead();
        this.createdAt = notifications.getCreatedAt();
        this.updatedAt = notifications.getUpdatedAt();
    }

    public NotificationsResponseDto(){
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
