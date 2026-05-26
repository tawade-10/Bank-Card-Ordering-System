package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Notification.Notifications;

import java.util.List;

public interface NotificationService {

    Notifications createNotification(Long customerId, String title, String message, String type, Long referenceId);

    List<Notifications> getUserNotifications(Long customerId, String type);

    void markAsRead(Long id);

    Notifications getLatestNotification(Long customerId);
}
