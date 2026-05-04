package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Notification.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getUserNotifications(Long userId);

    void notify(Long customerId, String cardRequestUpdate, String s);
}
