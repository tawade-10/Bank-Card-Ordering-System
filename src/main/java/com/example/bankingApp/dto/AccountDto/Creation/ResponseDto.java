package com.example.bankingApp.dto.AccountDto.Creation;

import java.time.LocalDateTime;

public class ResponseDto {

    private Long accountId;
    private String customerName;
    private String accountNumber;
    private String accountType;
    private String branchName;
    private String ifscCode;
    private String status;
    private Double balance;
    private LocalDateTime openedAt;
    private LocalDateTime updatedAt;
    private String message;

}
