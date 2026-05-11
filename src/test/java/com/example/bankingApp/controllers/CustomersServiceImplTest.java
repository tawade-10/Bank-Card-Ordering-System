package com.example.bankingApp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;

import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

class CustomerServiceImplTest {

    @Mock
    private CustomersRepo customersRepo;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_Successful() {

        CustomersRequestDto dto = new CustomersRequestDto();
        dto.setCustomerName("SHUBHAM");
        dto.setEmail("shubham10@gmail.com");
        dto.setPassword("shubham10");

        Customers saved = new Customers();
        saved.setCustomerId(1L);
        saved.setCustomerName("SHUBHAM");
        saved.setEmail("shubham10@gmail.com");
        saved.setRoles(Roles.CUSTOMER);
        saved.setPassword("shubham10");
        saved.setCreatedDate(LocalDate.now());
        saved.setCreatedTime(LocalTime.now());

        when(customersRepo.save(any(Customers.class))).thenReturn(saved);

        CustomersResponseDto response = authService.registerCustomer(dto);

        assertNotNull(response);
        assertEquals("SHUBHAM", response.getCustomerName());
        assertEquals("shubham10@gmail.com", response.getEmail());

        verify(customersRepo, times(1)).save(any(Customers.class));
    }
}