package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.service.Account.AccountService;

public class AccountFacadeImpl implements AccountFacade{

    private final AccountService accountService;

    public AccountFacadeImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountRequestsDto) {
        return accountService.createAccount(accountRequestsDto);
    }
}
