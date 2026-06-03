package com.example.bankingApp.service.Notifications;

import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Notification.Notifications;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.repository.Notifications.NotificationsRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationsRepo notificationsRepo;
    private final CustomersRepo customersRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationsServiceImpl(
            NotificationsRepo notificationsRepo,
            CustomersRepo customersRepo,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.notificationsRepo = notificationsRepo;
        this.customersRepo = customersRepo;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public NotificationsResponseDto createNotifications(NotificationsRequestDto notificationsRequestDto) {

        Customers customer = customersRepo.findById(notificationsRequestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<Notifications> existingOpt;

        if (notificationsRequestDto.getType().startsWith("LOGIN")
                || notificationsRequestDto.getType().startsWith("REGISTER")) {

            existingOpt = notificationsRepo
                    .findByCustomerCustomerIdAndType(notificationsRequestDto.getCustomerId(), notificationsRequestDto.getType());
        } else {
            existingOpt = notificationsRepo
                    .findByCustomerCustomerIdAndTypeAndReferenceId(
                            notificationsRequestDto.getCustomerId(),
                            notificationsRequestDto.getType(),
                            notificationsRequestDto.getReferenceId()
                    );
        }

        Notifications notification;
        if (existingOpt.isPresent()) {
            notification = existingOpt.get();
            notification.setTitle(notificationsRequestDto.getTitle());
            notification.setMessage(notificationsRequestDto.getMessage());
            notification.setReferenceId(notificationsRequestDto.getReferenceId());
            notification.setUpdatedAt(LocalDateTime.now());
        } else {
            notification = new Notifications();
            notification.setCustomer(customer);
            notification.setType(notificationsRequestDto.getType());
            notification.setTitle(notificationsRequestDto.getTitle());
            notification.setMessage(notificationsRequestDto.getMessage());
            notification.setReferenceId(notificationsRequestDto.getReferenceId());
            notification.setRead(false);
        }
        Notifications saved = notificationsRepo.save(notification);
        NotificationsResponseDto response = new NotificationsResponseDto(saved);
        messagingTemplate.convertAndSend(
                "/topic/notifications/" + notificationsRequestDto.getCustomerId(),
                response
        );
        return response;
    }

    @Override
    public List<NotificationsResponseDto> getAllNotificationsByUser(Long customerId) {
        return notificationsRepo
                .findByCustomerCustomerIdOrderByUpdatedAtDesc(customerId)
                .stream()
                .map(NotificationsResponseDto::new)
                .toList();
    }

    @Override
    public List<NotificationsResponseDto> getUserNotificationsByType(Long customerId, String type) {
        return notificationsRepo
                .findByCustomerCustomerIdAndTypeOrderByUpdatedAtDesc(customerId, type)
                .stream()
                .map(NotificationsResponseDto::new)
                .toList();
    }

    @Override
    public NotificationsResponseDto getLatestNotification(Long customerId) {
        return notificationsRepo
                .findTop1ByCustomerCustomerIdOrderByUpdatedAtDesc(customerId)
                .map(NotificationsResponseDto::new)
                .orElse(null);
    }

    @Override
    public List<NotificationsResponseDto> getRecentFiveNotifications(Long customerId) {
        return notificationsRepo
                .findTop5ByCustomerCustomerIdOrderByUpdatedAtDesc(customerId)
                .stream()
                .map(NotificationsResponseDto::new)
                .toList();
    }

    @Override
    public void markAsRead(Long id) {

        Notifications notification = notificationsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notification.setUpdatedAt(LocalDateTime.now());

        notificationsRepo.save(notification);
    }

    @Override
    public NotificationsResponseDto updateNotification(NotificationsRequestDto notificationsRequestDto) {

        Notifications notification = notificationsRepo
                .findByCustomerCustomerIdAndTypeAndReferenceId(
                        notificationsRequestDto.getCustomerId(),
                        notificationsRequestDto.getType(),
                        notificationsRequestDto.getReferenceId()
                )
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setTitle(notificationsRequestDto.getTitle());
        notification.setMessage(notificationsRequestDto.getMessage());
        notification.setRead(false);
        notification.setUpdatedAt(LocalDateTime.now());

        Notifications saved = notificationsRepo.save(notification);

        return new NotificationsResponseDto(saved);
    }
}

