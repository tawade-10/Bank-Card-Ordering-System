package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.service.Customers.CustomersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class AuthController {

    private final CustomersService customersService;

    public AuthController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CustomersResponseDto> registerCustomer(@Valid @RequestBody CustomersRequestDto customersRequestDto){
        CustomersResponseDto registeredCustomer = customersService.registerCustomer(customersRequestDto);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }
}
