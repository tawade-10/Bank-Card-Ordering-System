package com.example.bankingApp.service.Notifications;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.Notification.Notifications;

import java.util.List;

public interface NotificationsService {

    NotificationsResponseDto createNotification(NotificationsRequestDto notificationsRequestDto);

    List<NotificationsResponseDto> getUserNotifications(Long customerId, String type);

    void markAsRead(Long id);

    // Notifications getLatestNotification(Long customerId);
}
