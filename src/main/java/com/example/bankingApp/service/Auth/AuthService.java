package com.example.bankingApp.service.Auth;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    CustomersCreationResponseDto registerCustomer(@Valid CustomersCreationRequestDto customersCreationRequestDto);

    String generateResetToken(String email);

    String resetPassword(String token, String newPassword);
}
