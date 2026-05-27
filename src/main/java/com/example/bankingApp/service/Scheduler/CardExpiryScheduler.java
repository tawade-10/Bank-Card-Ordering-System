package com.example.bankingApp.service.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CardExpiryScheduler {

    private final CardExpiryBatchService batchService;

    public CardExpiryScheduler(CardExpiryBatchService batchService) {
        this.batchService = batchService;
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
    public void runDailyBatch() {
        batchService.runExpiryBatch();
        System.out.println("Card Expiry Batch every minute");
    }
}