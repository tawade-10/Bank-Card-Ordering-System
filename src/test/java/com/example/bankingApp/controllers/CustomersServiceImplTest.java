package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationRequestDto;
import com.example.bankingApp.dto.CustomersDto.CreationDto.CustomersCreationResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Roles;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Auth.AuthServiceImpl;
import com.example.bankingApp.service.Notifications.NotificationsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomersRepo customersRepo;

    @Mock
    private NotificationsServiceImpl notificationsServiceImpl;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_Successful() {

        CustomersCreationRequestDto dto = new CustomersCreationRequestDto();
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

        when(customersRepo.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(customersRepo.save(any(Customers.class))).thenReturn(saved);

        doNothing().when(notificationsServiceImpl).createNotifications(any(NotificationsRequestDto.class));

        CustomersCreationResponseDto response = authServiceImpl.registerCustomer(dto);

        assertNotNull(response);
        assertEquals("SHUBHAM", response.getCustomerName());
        assertEquals("shubham10@gmail.com", response.getEmail());
        assertEquals("User Registered Successfully!", response.getMessage());
        verify(customersRepo, times(1)).findByEmail("shubham10@gmail.com");
        verify(customersRepo, times(1)).save(any(Customers.class));
        verify(notificationsServiceImpl, times(1)).createNotifications(any(NotificationsRequestDto.class));
    }

    @Test
    void createCustomerAlreadyExists_ReturnsExistingCustomer() {

        CustomersCreationRequestDto dto = new CustomersCreationRequestDto();
        dto.setCustomerName("SHUBHAM");
        dto.setEmail("shubham10@gmail.com");
        dto.setPassword("shubham10");

        Customers existingCustomer = new Customers();
        existingCustomer.setCustomerId(1L);
        existingCustomer.setCustomerName("SHUBHAM");
        existingCustomer.setEmail("shubham10@gmail.com");
        existingCustomer.setRoles(Roles.CUSTOMER);

        when(customersRepo.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingCustomer));

        CustomersCreationResponseDto response =
                authServiceImpl.registerCustomer(dto);

        assertNotNull(response);
        assertEquals("SHUBHAM", response.getCustomerName());
        assertEquals("shubham10@gmail.com", response.getEmail());

        verify(customersRepo, never())
                .save(any(Customers.class));

        verify(notificationsServiceImpl, never())
                .createNotifications(any());
    }

    @Test
    void createCustomerNull_ThrowsException() {

        CustomersCreationRequestDto dto =
                new CustomersCreationRequestDto();

        dto.setCustomerName(null);
        dto.setEmail("shubham10@gmail.com");
        dto.setPassword("shubham10");

        assertThrows(RuntimeException.class,
                () -> authServiceImpl.registerCustomer(dto));
    }
}