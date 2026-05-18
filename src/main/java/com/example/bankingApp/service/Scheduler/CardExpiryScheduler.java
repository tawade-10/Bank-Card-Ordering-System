package com.example.bankingApp.service.Scheduler;

import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
public class CardExpiryScheduler {

    @Autowired
    private CardDetailsRepo cardDetailsRepo;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkCardExpiry() {

        YearMonth now = YearMonth.now();
        YearMonth threshold = now.plusMonths(1);

        List<CardDetails> allCards = cardDetailsRepo.findAll();

        for (CardDetails card : allCards) {

            YearMonth expiry = card.getExpiry();

            if (expiry != null &&
                    (expiry.equals(threshold) || expiry.isBefore(threshold)) &&
                    !expiry.isBefore(now)) {

                Long userId = card.getCustomers().getCustomerId();

                String title = "Card Expiring Soon";

                String message = "Your " + card.getCardType().getTypeName() +
                        " card ending with " + mask(card.getCardNumber()) +
                        " will expire on " + expiry;

                notificationService.sendNotification(
                        userId,
                        title,
                        message,
                        "CARD_EXPIRY",
                        card.getCardId()
                );
            }
        }
    }

    private String mask(String cardNumber) {
        return "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
    }
}