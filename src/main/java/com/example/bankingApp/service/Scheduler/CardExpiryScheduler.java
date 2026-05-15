package com.example.bankingApp.service.Scheduler;

import com.example.bankingApp.dto.ExpiryNotificationsDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CardExpiryScheduler {

    @Autowired
    private CardDetailsRepo cardDetailsRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkCardExpiry() {

        LocalDate today = LocalDate.now();
        LocalDate oneMonthLater = today.plusMonths(1);

        List<CardDetails> expiringCards =
                cardDetailsRepo.findByExpiryDateBetween(today, oneMonthLater);

        for (CardDetails card : expiringCards) {

            ExpiryNotificationsDto dto = new ExpiryNotificationsDto(
                    card.getCardNumber(),
                    card.getExpiry(),
                    card.getCustomers().getCustomerName()
            );

            messagingTemplate.convertAndSend("/topic/expiryNotifications", dto);
        }
    }
}