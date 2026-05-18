package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Notification.Notification;
import com.example.bankingApp.repository.Notification.NotificationRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepo notificationRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepo notificationRepo, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepo = notificationRepo;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public Notification sendNotification(Long userId, String title, String message) {
        Notification notification = new Notification(userId, title, message, "GENERAL", null);
        notificationRepo.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
        return notification;
    }

    @Override
    public Notification sendNotification(Long userId, String title, String message,
                                         String type, Long referenceId) {

        Notification existing = notificationRepo
                .findByUserIdAndTypeAndReferenceId(userId, type, referenceId)
                .orElse(null);

        if (existing != null) {
            existing.setMessage(message);
            existing.setTitle(title);
            existing.setRead(false);
            existing.setUpdatedAt(LocalDateTime.now());
            notificationRepo.save(existing);
            messagingTemplate.convertAndSend("/topic/notifications/" + userId, existing);
            return existing;
        }

        Notification notification = new Notification(userId, title, message, type, referenceId);
        notificationRepo.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
        return notification;
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public void markAsRead(Long id) {
        notificationRepo.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepo.save(notification);
        });
    }

    @Override
    public Notification getLatestNotification(Long userId) {
        return notificationRepo.findTopByUserIdOrderByUpdatedAtDesc(userId);
    }
}
