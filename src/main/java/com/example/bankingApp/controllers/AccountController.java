package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.AccountDto.AccountCreationRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.facade.Account.AccountFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> createAccountRequest(@Valid @RequestBody AccountCreationRequestDto accountRequestsDto){
        AccountResponseDto accountCreated = accountFacade.createAccountRequest(accountRequestsDto);
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
    }

}
