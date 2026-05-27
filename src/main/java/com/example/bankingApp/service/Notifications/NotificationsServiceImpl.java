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
import java.util.stream.Collectors;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationsRepo notificationsRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final CustomersRepo customersRepo;

    public NotificationsServiceImpl(NotificationsRepo notificationsRepo,
                                    SimpMessagingTemplate messagingTemplate,
                                    CustomersRepo customersRepo) {
        this.notificationsRepo = notificationsRepo;
        this.messagingTemplate = messagingTemplate;
        this.customersRepo = customersRepo;
    }

    @Override
    public NotificationsResponseDto createNotification(NotificationsRequestDto notificationsRequestDto) {

        Customers customer = customersRepo.findById(notificationsRequestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<Notifications> existing =
                notificationsRepo.findByCustomerCustomerIdAndTypeAndReferenceId(
                        notificationsRequestDto.getCustomerId(),
                        notificationsRequestDto.getType(),
                        notificationsRequestDto.getReferenceId()
                );

        Notifications n;

        if (existing.isPresent()) {
            n = existing.get();
            n.setTitle(notificationsRequestDto.getTitle());
            n.setMessage(notificationsRequestDto.getMessage());
            n.setUpdatedAt(LocalDateTime.now());
        } else {
            n = new Notifications();
            n.setCustomer(customer);
            n.setTitle(notificationsRequestDto.getTitle());
            n.setMessage(notificationsRequestDto.getMessage());
            n.setType(notificationsRequestDto.getType());
            n.setReferenceId(notificationsRequestDto.getReferenceId());
            n.setCreatedAt(LocalDateTime.now());
            n.setUpdatedAt(LocalDateTime.now());
        }

        Notifications saved = notificationsRepo.save(n);

        NotificationsResponseDto response = toResponse(saved);

        messagingTemplate.convertAndSend("/topic/notifications/" + notificationsRequestDto.getCustomerId(), response);

        return response;
    }


    @Override
    public List<NotificationsResponseDto> getUserNotifications(Long customerId, String type) {

        customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Notifications> list = (type == null || type.isEmpty())
                ? notificationsRepo.findByCustomerCustomerIdOrderByCreatedAtDesc(customerId)
                : notificationsRepo.findByCustomerCustomerIdAndTypeOrderByCreatedAtDesc(customerId, type);

        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }


    @Override
    public void markAsRead(Long id) {
        Notifications n = notificationsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        notificationsRepo.save(n);
    }


    private NotificationsResponseDto toResponse(Notifications n) {
        return new NotificationsResponseDto(
                n.getId(),
                n.getTitle(),
                n.getMessage(),
                n.getType(),
                n.getReferenceId(),
                n.isRead(),
                n.getCreatedAt(),
                n.getUpdatedAt()
        );
    }
}