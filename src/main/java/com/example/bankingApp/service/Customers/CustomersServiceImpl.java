package com.example.bankingApp.service.Customers;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.CustomersDto.UpdateDto.CustomersUpdateResponseDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public List<CustomersCreationResponseDto> getAllCustomers() {
        List<Customers> customers = customersRepo.findAll();
        return customers.stream().map(CustomersCreationResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public CustomersCreationResponseDto getCustomerById(Long customerId) {
        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        return new CustomersCreationResponseDto(customer);
    }

    @Override
    public CustomersUpdateResponseDto updateCustomer(Long customerId, CustomersCreationRequestDto customersCreationRequestDto) {

        Customers customer = customersRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        customer.setCustomerName(customersCreationRequestDto.getCustomerName());
        customer.setEmail(customersCreationRequestDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customersCreationRequestDto.getPassword()));
        customer.setRoles(Roles.CUSTOMER);
        customer.setUpdatedDate(LocalDate.now());
        customer.setUpdatedTime(LocalTime.now());

        Customers saveUpdatedCustomer = customersRepo.save(customer);
        return new CustomersUpdateResponseDto(saveUpdatedCustomer);
    }
}
