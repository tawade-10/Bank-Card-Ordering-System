package com.example.bankingApp.facade.CustomersFacade;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;

import java.util.List;

public interface CustomersFacade {
    List<CustomersResponseDto> getAllCustomers();

    CustomersResponseDto getCustomerById(Long customerId);

    CustomersResponseDto updateCustomer(Long customerId, CustomersRequestDto customersRequestDto);
}
