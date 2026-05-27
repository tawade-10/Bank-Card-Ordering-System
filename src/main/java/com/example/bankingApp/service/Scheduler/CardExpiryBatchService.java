package com.example.bankingApp.service.Scheduler;

import com.example.bankingApp.dto.Notifications.NotificationsResponseDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardDetails.CardExpiryBatch;
import com.example.bankingApp.entity.Notification.Notifications;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.repository.CardDetails.CardExpiryBatchRepo;
import com.example.bankingApp.repository.Notifications.NotificationsRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class CardExpiryBatchService {

    private final CardDetailsRepo cardDetailsRepo;
    private final CardExpiryBatchRepo batchRepo;
    private final NotificationsRepo notificationsRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public CardExpiryBatchService(CardDetailsRepo cardDetailsRepo, CardExpiryBatchRepo batchRepo,
                                  NotificationsRepo notificationsRepo, SimpMessagingTemplate messagingTemplate) {
        this.cardDetailsRepo = cardDetailsRepo;
        this.batchRepo = batchRepo;
        this.notificationsRepo = notificationsRepo;
        this.messagingTemplate = messagingTemplate;
    }

    public void runExpiryBatch() {

        LocalDate today = LocalDate.now();
        YearMonth target = YearMonth.from(today.plusDays(30));

        List<CardDetails> expiringCards = cardDetailsRepo.findByExpiryLessThanEqual(target);

        if (expiringCards.isEmpty()) return;

        CardExpiryBatch batch = new CardExpiryBatch();
        batch.setRunDate(today);
        batch.setCardsExpiringCount(expiringCards.size());
        batchRepo.save(batch);

        for (CardDetails card : expiringCards) {

            Optional<Notifications> existing = notificationsRepo.findByCustomerCustomerIdAndTypeAndReferenceId(
                            card.getCustomers().getCustomerId(),
                            "CARD_EXPIRY",
                            card.getCardId()
                    );

            if (existing.isPresent()) continue;

            String title = "Card Expiry Reminder";
            String msg = "Your " + card.getCardType().getTypeName() +
                    " card will expire on " + card.getExpiry();

            Notifications notification = new Notifications();
            notification.setCustomer(card.getCustomers());
            notification.setTitle(title);
            notification.setMessage(msg);
            notification.setType("CARD_EXPIRY");
            notification.setReferenceId(card.getCardId());
            notification.setCreatedAt(LocalDateTime.now());
            notification.setUpdatedAt(LocalDateTime.now());

            notificationsRepo.save(notification);

            // Convert to DTO
            NotificationsResponseDto dto = new NotificationsResponseDto(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getType(),
                    notification.getReferenceId(),
                    notification.isRead(),
                    notification.getCreatedAt(),
                    notification.getUpdatedAt()
            );

            messagingTemplate.convertAndSend("/topic/notifications/" + card.getCustomers().getCustomerId(), dto);
        }
    }
}