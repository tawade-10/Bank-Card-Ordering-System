package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.facade.AuthFacade.AuthFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/customers")
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomersResponseDto> registerCustomer(@Valid @RequestBody CustomersRequestDto customersRequestDto){
        CustomersResponseDto registeredCustomer = authFacade.registerCustomer(customersRequestDto);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomersRequestDto customersRequestDto) {
        Object loginCustomer = authFacade.loginCustomer(customersRequestDto);
        return ResponseEntity.ok(loginCustomer);
    }

}
