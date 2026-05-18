package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Notification.Notification;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(Long userId, String title, String message);

    Notification sendNotification(
            Long userId, String title, String message,
            String type, Long referenceId
    );

    List<Notification> getUserNotifications(Long userId);

    void markAsRead(Long id);

    Notification getLatestNotification(Long userId);
}
