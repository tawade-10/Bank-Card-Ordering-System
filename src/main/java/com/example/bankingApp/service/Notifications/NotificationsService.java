package com.example.bankingApp.service.Notifications;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;

import java.util.List;

public interface NotificationsService {
    
    NotificationsResponseDto createNotifications(NotificationsRequestDto notificationsRequestDto);

    List<NotificationsResponseDto> getAllNotificationsByUser(Long customerId);

    List<NotificationsResponseDto> getUserNotificationsByType(Long customerId, String type);

    NotificationsResponseDto getLatestNotification(Long customerId);

    List<NotificationsResponseDto> getRecentFiveNotifications(Long customerId);

    void markAsRead(Long id);

    NotificationsResponseDto updateNotification(NotificationsRequestDto notificationsRequestDto);
}
