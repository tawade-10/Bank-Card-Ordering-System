package com.example.bankingApp.service.Auth;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    CustomersResponseDto registerCustomer(@Valid CustomersRequestDto customersRequestDto);
}
