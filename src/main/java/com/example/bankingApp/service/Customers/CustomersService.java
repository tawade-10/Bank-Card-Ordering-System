package com.example.bankingApp.service.Customers;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.UpdateDto.CustomersUpdateResponseDto;

import java.util.List;

public interface CustomersService {
    List<CustomersCreationResponseDto> getAllCustomers();

    CustomersCreationResponseDto getCustomerById(Long customerId);

    CustomersUpdateResponseDto updateCustomer(Long customerId, CustomersCreationRequestDto customersCreationRequestDto);
}
