package com.example.bankingApp.service.Notifications;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.Notification.Notifications;

import java.util.List;

public interface NotificationsService {
    
    NotificationsResponseDto createNotifications(NotificationsRequestDto dto);

    List<NotificationsResponseDto> getAllNotificationsByUser(Long customerId);

    List<NotificationsResponseDto> getUserNotificationsByType(Long customerId, String type);

    NotificationsResponseDto getLatestNotification(Long customerId);

    List<NotificationsResponseDto> getRecentFiveNotifications(Long customerId);

    void markAsRead(Long id);
}
