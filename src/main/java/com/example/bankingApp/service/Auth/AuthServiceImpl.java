package com.example.bankingApp.service.Auth;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomersRepo customersRepo;

    private final NotificationService notificationService;

    public AuthServiceImpl(CustomersRepo customersRepo, NotificationService notificationService) {
        this.customersRepo = customersRepo;
        this.notificationService = notificationService;
    }

    @Override
    public CustomersResponseDto registerCustomer(CustomersRequestDto customersRequestDto) {

        Customers customer = new Customers();

        Optional<Customers> existingCustomerByEmail = customersRepo.findByEmail(customersRequestDto.getEmail());
        if (existingCustomerByEmail.isPresent()) {
            return new CustomersResponseDto(existingCustomerByEmail.get());
        }

        customer.setCustomerName(customersRequestDto.getCustomerName());
        customer.setEmail(customersRequestDto.getEmail());
        customer.setRoles(Roles.CUSTOMER);
        customer.setPassword(customersRequestDto.getPassword());
        customer.setCreatedDate(LocalDate.now());
        customer.setCreatedTime(LocalTime.now());
        Customers savedCustomer = customersRepo.save(customer);

        notificationService.sendNotification(
                savedCustomer.getCustomerId(),
                "Registration Successful",
                "Welcome to our banking system!",
                "REGISTER",
                savedCustomer.getCustomerId()
        );

        return new CustomersResponseDto(savedCustomer);
    }
}
