package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.AccountCreationRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;

public interface AccountService {
    AccountResponseDto createAccountRequest(AccountCreationRequestDto accountRequestsDto);
}
