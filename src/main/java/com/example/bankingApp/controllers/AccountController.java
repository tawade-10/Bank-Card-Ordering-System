package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.AccountDto.Creation.CreationRequestDto;
import com.example.bankingApp.dto.AccountDto.Creation.CreationResponseDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountRequestDto;
import com.example.bankingApp.dto.AccountDto.Request.AccountResponseDto;
import com.example.bankingApp.entity.Enums.AccountStatus;
import com.example.bankingApp.facade.Account.AccountFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping("/create-request")
    public ResponseEntity<AccountResponseDto> createAccountRequest(@Valid @RequestBody AccountRequestDto accountRequestsDto){
        AccountResponseDto requestCreated = accountFacade.createAccountRequest(accountRequestsDto);
        return new ResponseEntity<>(requestCreated, HttpStatus.CREATED);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AccountResponseDto>> getPendingRequests(){
        List<AccountResponseDto> pendingRequests = accountFacade.getPendingRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<AccountResponseDto> getRequestById(@PathVariable Long requestId){
        AccountResponseDto requestById = accountFacade.getRequestById(requestId);
        return ResponseEntity.ok(requestById);
    }

    @PostMapping("/create-account")
    public ResponseEntity<CreationResponseDto> createAccount(@Valid @RequestBody CreationRequestDto creationRequestDto){
        CreationResponseDto accountCreated = accountFacade.createAccount(creationRequestDto);
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<CreationResponseDto>> getAllAccounts(){
        List<CreationResponseDto> allAccounts = accountFacade.getAllAccounts();
        return ResponseEntity.ok(allAccounts);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<CreationResponseDto> getAccountById(@PathVariable Long accountId){
        CreationResponseDto accountById = accountFacade.getAccountById(accountId);
        return ResponseEntity.ok(accountById);
    }

    @PutMapping("/update/{requestId}")
    public ResponseEntity<AccountResponseDto> updateAccountStatus(@PathVariable Long requestId, @RequestParam AccountStatus accountStatus) {
        AccountResponseDto updated = accountFacade.updateAccountStatus(requestId, accountStatus);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<CreationResponseDto>> getUserAccounts(){
        List<CreationResponseDto> userAccounts = accountFacade.getUserAccounts();
        return ResponseEntity.ok(userAccounts);
    }
}
