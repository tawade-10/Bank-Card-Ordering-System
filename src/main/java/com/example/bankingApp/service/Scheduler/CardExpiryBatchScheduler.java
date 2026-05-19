package com.example.bankingApp.service.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CardExpiryBatchScheduler {

    private final CardExpiryBatchService batchService;

    public CardExpiryBatchScheduler(CardExpiryBatchService batchService) {
        this.batchService = batchService;
    }

    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
    public void runDailyBatch() {
        batchService.runExpiryBatch();
        System.out.println("Card Expiry Batch executed at 2 AM");
    }
}
