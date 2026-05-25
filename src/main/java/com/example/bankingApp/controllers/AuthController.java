package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.facade.AuthFacade.AuthFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomersCreationResponseDto> registerCustomer(@Valid @RequestBody CustomersCreationRequestDto customersCreationRequestDto){
        CustomersCreationResponseDto registeredCustomer = authFacade.registerCustomer(customersCreationRequestDto);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomersCreationRequestDto customersCreationRequestDto) {
        Object loginCustomer = authFacade.loginCustomer(customersCreationRequestDto);
        return ResponseEntity.ok(loginCustomer);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        String response = authFacade.generateResetToken(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String response = authFacade.resetPassword(token, newPassword);
        return ResponseEntity.ok(response);
    }
}
