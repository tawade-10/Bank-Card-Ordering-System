package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @PostMapping("/create")
    public NotificationsResponseDto createNotifications(@RequestBody NotificationsRequestDto dto) {
        return notificationsService.createNotifications(dto);
    }

    @GetMapping("/user/{customerId}")
    public List<NotificationsResponseDto> getAllNotificationsByUser(@PathVariable Long customerId) {
        return notificationsService.getAllNotificationsByUser(customerId);
    }

    @GetMapping("/user/{customerId}/type/{type}")
    public List<NotificationsResponseDto> getUserNotificationsByType(@PathVariable Long customerId, @PathVariable String type) {
        return notificationsService.getUserNotificationsByType(customerId, type);
    }

    @GetMapping("/latest/{customerId}")
    public NotificationsResponseDto getLatestNotification(@PathVariable Long customerId) {
        return notificationsService.getLatestNotification(customerId);
    }

    @GetMapping("/recent-five/{customerId}")
    public List<NotificationsResponseDto> getRecentFiveNotifications(@PathVariable Long customerId) {
        return notificationsService.getRecentFiveNotifications(customerId);
    }

    @PutMapping("/update")
    public NotificationsResponseDto updateNotification(@RequestBody NotificationsRequestDto notificationsRequestDto) {
        return notificationsService.updateNotification(notificationsRequestDto);
    }

    @PutMapping("/read/{id}")
    public String markRead(@PathVariable Long id) {
        notificationsService.markAsRead(id);
        return "Notification marked as read";
    }
}