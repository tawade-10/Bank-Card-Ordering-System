package com.example.bankingApp.facade.CustomersFacade;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.UpdateDto.CustomersUpdateResponseDto;

import java.util.List;

public interface CustomersFacade {
    List<CustomersCreationResponseDto> getAllCustomers();

    CustomersCreationResponseDto getCustomerById(Long customerId);

    CustomersUpdateResponseDto updateCustomer(Long customerId, CustomersCreationRequestDto customersCreationRequestDto);
}
