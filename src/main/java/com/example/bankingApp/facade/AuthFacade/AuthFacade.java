package com.example.bankingApp.facade.AuthFacade;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import jakarta.validation.Valid;

public interface AuthFacade {
    CustomersCreationResponseDto registerCustomer(@Valid CustomersCreationRequestDto customersCreationRequestDto);

    Object loginCustomer(CustomersCreationRequestDto customersCreationRequestDto);

    String generateResetToken(String email);

    String resetPassword(String token, String newPassword);
}
