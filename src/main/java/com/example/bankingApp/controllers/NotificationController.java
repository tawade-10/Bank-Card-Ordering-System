package com.example.bankingApp.controllers;

import com.example.bankingApp.entity.Notification.Notifications;
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

    @PostMapping("/create")
    public Notifications create(
            @RequestParam Long customerId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String type,
            @RequestParam(required = false) Long referenceId
    ){
        return notificationService.createNotification(customerId, title, message, type, referenceId);
    }

    @GetMapping("/user/{customerId}")
    public List<Notifications> getUserNotifications(@PathVariable Long customerId, String type) {
        return notificationService.getUserNotifications(customerId,type);
    }

    @PutMapping("/read/{id}")
    public String markRead(@PathVariable Long id){
        notificationService.markAsRead(id);
        return "Notification marked as read";
    }

    @GetMapping("/latest/{customerId}")
    public Notifications getLatestNotification(Long customerId){
        return notificationService.getLatestNotification(customerId);
    }
}