package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Notification.Notification;
import com.example.bankingApp.repository.Notification.NotificationRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepo notificationRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationServiceImpl(NotificationRepo notificationRepo, SimpMessagingTemplate simpMessagingTemplate) {
        this.notificationRepo = notificationRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notify(Long userId, String title, String message) {

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepo.save(notification);

        simpMessagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
