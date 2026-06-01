package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto accountRequestsDto);
}
