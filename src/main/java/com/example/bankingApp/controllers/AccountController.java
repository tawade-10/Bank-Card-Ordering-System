package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.AccountDto.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.AccountResponseDto;
import com.example.bankingApp.dto.CardRequestsDto.CreationDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CreationDto.ResponseDto;
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
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountRequestsDto){
        AccountResponseDto accountCreated = accountFacade.createAccount(accountRequestsDto);
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
    }

}
