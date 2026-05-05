package com.example.bankingApp.controllers;

import com.example.bankingApp.entity.Notification.Notification;
import com.example.bankingApp.service.Notification.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/notify/{userId}")
    public String testNotify(@PathVariable Long userId) {
        notificationService.sendNotification(
                userId,
                "Test Notification",
                "This is a test message!"
        );

        return "Notification Sent!";
    }
}
