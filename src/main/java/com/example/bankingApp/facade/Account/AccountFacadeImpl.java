package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.AccountCreationRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.service.Account.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountFacadeImpl implements AccountFacade{

    private final AccountService accountService;

    public AccountFacadeImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountResponseDto createAccountRequest(AccountCreationRequestDto accountRequestsDto) {
        return accountService.createAccountRequest(accountRequestsDto);
    }
}
