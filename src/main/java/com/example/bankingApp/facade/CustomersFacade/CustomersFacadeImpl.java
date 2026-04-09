package com.example.bankingApp.facade.CustomersFacade;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
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
    public List<CustomersResponseDto> getAllCustomers() {
        return customersService.getAllCustomers();
    }

    @Override
    public CustomersResponseDto getCustomerById(Long customerId) {
        return customersService.getCustomerById(customerId);
    }

    @Override
    public CustomersResponseDto updateCustomer(Long customerId, CustomersRequestDto customersRequestDto) {
        return customersService.updateCustomer(customerId,customersRequestDto);
    }
}
