package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.service.Notifications.NotificationsService;
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
    public NotificationsResponseDto create(@RequestBody NotificationsRequestDto notificationsRequestDto) {
        return notificationsService.createNotification(notificationsRequestDto);
    }

    @GetMapping("/user/{customerId}")
    public List<NotificationsResponseDto> getUserNotifications(@PathVariable Long customerId, @RequestParam(required = false) String type) {
        return notificationsService.getUserNotifications(customerId, type);
    }

    @PutMapping("/read/{id}")
    public String markRead(@PathVariable Long id) {
        notificationsService.markAsRead(id);
        return "Notification marked as read";
    }
}