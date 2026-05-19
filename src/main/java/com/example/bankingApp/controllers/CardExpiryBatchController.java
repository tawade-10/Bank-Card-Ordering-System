package com.example.bankingApp.controllers;

import com.example.bankingApp.service.Scheduler.CardExpiryBatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class CardExpiryBatchController {

    private final CardExpiryBatchService batchService;

    public CardExpiryBatchController(CardExpiryBatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/run")
    public String runBatchManually() {
        batchService.runExpiryBatch();
        return "Batch executed manually!";
    }
}
