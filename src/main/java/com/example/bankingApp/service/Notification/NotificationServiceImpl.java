package com.example.bankingApp.service.Notification;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Notification.Notifications;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.repository.Notification.NotificationRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final CustomersRepo customersRepo;

    public NotificationServiceImpl(NotificationRepo notificationRepo, SimpMessagingTemplate messagingTemplate, CustomersRepo customersRepo) {
        this.notificationRepo = notificationRepo;
        this.messagingTemplate = messagingTemplate;
        this.customersRepo = customersRepo;
    }

    public Notifications createNotification(Long customerId, String title,
                                            String message, String type, Long referenceId) {

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<Notifications> existing = notificationRepo
                .findByCustomerCustomerIdAndTypeAndReferenceId(customerId, type, referenceId);

        Notifications n;

        if (existing.isPresent()) {
            n = existing.get();
            n.setMessage(message);
            n.setTitle(title);
            n.setUpdatedAt(LocalDateTime.now());
        } else {
            n = new Notifications();
            n.setCustomer(customer);
            n.setTitle(title);
            n.setMessage(message);
            n.setType(type);
            n.setReferenceId(referenceId);
            n.setCreatedAt(LocalDateTime.now());
            n.setUpdatedAt(LocalDateTime.now());
        }

        Notifications saved = notificationRepo.save(n);
        messagingTemplate.convertAndSend(
                "/topic/notifications/" + customerId,
                saved
        );
        return saved;
    }

    @Override
    public List<Notifications> getUserNotifications(Long customerId, String type) {

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return notificationRepo.findByCustomerCustomerIdAndTypeOrderByCreatedAtDesc(
                customerId,
                type
        );
    }

    @Override
    public void markAsRead(Long id) {
        Notifications notification = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepo.save(notification);
    }

    @Override
    public Notifications getLatestNotification(Long customerId) {
        return notificationRepo.findTop1ByCustomerCustomerIdOrderByUpdatedAtDesc(customerId);
    }
}