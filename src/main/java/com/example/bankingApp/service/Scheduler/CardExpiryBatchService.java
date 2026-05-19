package com.example.bankingApp.service.Scheduler;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardDetails.CardExpiryBatch;
import com.example.bankingApp.entity.Notification.Notification;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.repository.CardDetails.CardExpiryBatchRepo;
import com.example.bankingApp.repository.Notification.NotificationRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class CardExpiryBatchService {

    private final CardDetailsRepo cardDetailsRepo;
    private final CardExpiryBatchRepo batchRepo;
    private final NotificationRepo notificationRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public CardExpiryBatchService(CardDetailsRepo cardDetailsRepo, CardExpiryBatchRepo batchRepo, NotificationRepo notificationRepo, SimpMessagingTemplate messagingTemplate) {
        this.cardDetailsRepo = cardDetailsRepo;
        this.batchRepo = batchRepo;
        this.notificationRepo = notificationRepo;
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

            String title = "Card Expiry Reminder";
            String msg = "Your " + card.getCardType().getTypeName() + " card will expire on "
                    + card.getExpiry();

            Notification notification = new Notification(
                    card.getCustomers().getCustomerId(),
                    title,
                    msg,
                    "CARD_EXPIRY",
                    card.getCardId()
            );

            notificationRepo.save(notification);
            messagingTemplate.convertAndSend(
                    "/topic/notifications/" + card.getCustomers().getCustomerId(),
                    msg
            );
        }
    }
}