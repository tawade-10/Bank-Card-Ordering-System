package com.example.bankingApp.facade.CustomersFacade;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.UpdateDto.CustomersUpdateResponseDto;
import com.example.bankingApp.service.Customers.CustomersService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomersFacadeImpl implements CustomersFacade{

    private final CustomersService customersService;

    public CustomersFacadeImpl(CustomersService customersService) {
        this.customersService = customersService;
    }

    @Override
    public List<CustomersCreationResponseDto> getAllCustomers() {
        return customersService.getAllCustomers();
    }

    @Override
    public CustomersCreationResponseDto getCustomerById(Long customerId) {
        return customersService.getCustomerById(customerId);
    }

    @Override
    public CustomersUpdateResponseDto updateCustomer(Long customerId, CustomersCreationRequestDto customersCreationRequestDto) {
        return customersService.updateCustomer(customerId, customersCreationRequestDto);
    }
}
