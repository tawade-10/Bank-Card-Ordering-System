package com.example.bankingApp.service.Customers;

import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;

import java.util.List;

public interface CustomersService {
    List<CustomersResponseDto> getAllCustomers();

    CustomersResponseDto getCustomerById(Long customerId);
}
