package com.example.bankingApp.controllers;

import com.example.bankingApp.entity.Notification.Notification;
import com.example.bankingApp.service.Notification.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PatchMapping("/read/{id}")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "Notification marked as read";
    }

    @GetMapping("/notify/{userId}")
    public String testNotify(@PathVariable Long userId) {
        notificationService.sendNotification(userId, "Test Notification", "This is a test message!", "TEST", 1L);
        return "Notification Sent!";
    }

    @GetMapping("/latest/{userId}")
    public Notification getLatestNotification(@PathVariable Long userId) {
        return notificationService.getLatestNotification(userId);
    }
}