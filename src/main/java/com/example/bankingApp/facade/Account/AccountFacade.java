package com.example.bankingApp.facade.Account;

import com.example.bankingApp.dto.AccountDto.AccountCreationRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import jakarta.validation.Valid;

public interface AccountFacade {

    AccountResponseDto createAccountRequest(@Valid AccountCreationRequestDto accountRequestsDto);
}
