package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;
import com.example.bankingApp.service.Account.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountFacadeImpl implements AccountFacade{

    private final AccountService accountService;

    public AccountFacadeImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountResponseDto createAccountRequest(AccountRequestDto accountRequestsDto) {
        return accountService.createAccountRequest(accountRequestsDto);
    }

    @Override
    public List<AccountResponseDto> getPendingRequests() {
        return accountService.getPendingRequests();
    }

    @Override
    public CreationResponseDto createAccount(CreationRequestDto creationRequestDto) {
        return accountService.createAccount(creationRequestDto);
    }
}
