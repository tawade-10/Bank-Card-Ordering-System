package com.example.bankingApp.service.Account;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccountRequest(AccountRequestDto accountRequestsDto);

    List<AccountResponseDto> getPendingRequests();

    CreationResponseDto createAccount(CreationRequestDto creationRequestDto);
}
