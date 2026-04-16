package com.example.bankingApp.service.Customers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersServiceImpl implements CustomersService{

    private final CustomersRepo customersRepo;

    private final PasswordEncoder passwordEncoder;

    public CustomersServiceImpl(CustomersRepo customersRepo, PasswordEncoder passwordEncoder) {
        this.customersRepo = customersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CustomersResponseDto> getAllCustomers() {
        List<Customers> customers = customersRepo.findAll();
        return customers.stream().map(CustomersResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CustomersResponseDto getCustomerById(Long customerId) {
        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        return new CustomersResponseDto(customer);
    }

    @Override
    public CustomersResponseDto updateCustomer(Long customerId, CustomersRequestDto customersRequestDto) {

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        customer.setCustomerName(customersRequestDto.getCustomerName());
        customer.setEmail(customersRequestDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customersRequestDto.getPassword()));
        customer.setRoles(customersRequestDto.getRoles());

        Customers saveUpdatedCustomer = customersRepo.save(customer);
        return new CustomersResponseDto(saveUpdatedCustomer);
    }
}
