package com.example.bankingApp.facade.AuthFacade;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import jakarta.validation.Valid;

public interface AuthFacade {
    CustomersResponseDto registerCustomer(@Valid CustomersRequestDto customersRequestDto);

    Object loginCustomer(CustomersRequestDto customersRequestDto);
}
