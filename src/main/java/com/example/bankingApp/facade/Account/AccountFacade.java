package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;
import com.example.bankingApp.entity.Enums.AccountStatus;
import jakarta.validation.Valid;

import java.util.List;

public interface AccountFacade {

    AccountResponseDto createAccountRequest(@Valid AccountRequestDto accountRequestsDto);

    List<AccountResponseDto> getPendingRequests();

    CreationResponseDto createAccount(@Valid CreationRequestDto creationRequestDto);

    List<CreationResponseDto> getAllAccounts();

    CreationResponseDto getAccountById(Long accountId);

    AccountResponseDto getRequestById(Long requestId);

    AccountResponseDto updateAccountStatus(Long requestId, AccountStatus accountStatus);

    List<CreationResponseDto> getUserAccounts();
}
