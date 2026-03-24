package com.example.bankingApp.service.Customers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.repository.CustomersRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CustomersServiceImpl implements CustomersService{

    private final CustomersRepo customersRepo;

    public CustomersServiceImpl(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }

    @Override
    public CustomersResponseDto registerCustomer(CustomersRequestDto customersRequestDto) {

        Customers customer = new Customers();

        customer.setCustomerName(customersRequestDto.getCustomerName());
        customer.setEmail(customersRequestDto.getEmail());
        customer.setPassword(customersRequestDto.getPassword());
        customer.setCreatedDate(LocalDate.now());
        customer.setCreatedTime(LocalTime.now());
        Customers savedCustomer = customersRepo.save(customer);

        return new CustomersResponseDto(savedCustomer);
    }

    @Override
    public Object loginCustomer(CustomersRequestDto customersRequestDto) {
        return null;
    }
}
