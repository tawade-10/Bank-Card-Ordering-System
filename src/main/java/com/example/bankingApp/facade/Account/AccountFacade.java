package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;

public interface AccountFacade {
    AccountResponseDto createAccount(AccountRequestDto accountRequestsDto);
}
