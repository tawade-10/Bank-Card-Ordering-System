package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.CreationDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CreationDto.ResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.entity.CardRequests.*;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.RequestStatus;
import com.example.bankingApp.repository.Bank.AccountCreationRepo;
import com.example.bankingApp.repository.CardRequests.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.CardRequests.CardRequestsServiceImpl;
import com.example.bankingApp.service.Notifications.NotificationsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardRequestsServiceImplTest {

    @Mock
    private CardRequestsRepo cardRequestsRepo;

    @Mock
    private CustomersRepo customersRepo;

    @Mock
    private CardTypeRepo cardTypeRepo;

    @Mock
    private CardVariantRepo cardVariantRepo;

    @Mock
    private CardNetworkRepo cardNetworkRepo;

    @Mock
    private NetworkBinRepo networkBinRepo;

    @Mock
    private AccountCreationRepo accountCreationRepo;

    @Mock
    private ReasonForRequestRepo reasonForRequestRepo;

    @Mock
    private NotificationsServiceImpl notificationsServiceImpl;

    @InjectMocks
    private CardRequestsServiceImpl cardRequestsServiceImpl;

    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createRequest_successful() {

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test@gmail.com");

        RequestsDto dto = new RequestsDto();
        dto.setCardTypeId(1L);
        dto.setCardVariantId(2L);
        dto.setCardNetworkId(3L);
        dto.setReasonId(4L);

        Customers customer = new Customers();
        customer.setCustomerId(10L);
        customer.setEmail("test@gmail.com");

        CardType cardType = new CardType();
        cardType.setTypeName("DEBIT");

        CardVariant cardVariant = new CardVariant();
        CardNetwork cardNetwork = new CardNetwork();
        NetworkBin networkBin = new NetworkBin();
        Reason reason = new Reason();

        when(customersRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(customer));
        when(accountCreationRepo.existsByCustomer(customer)).thenReturn(true);
        when(cardTypeRepo.findById(1L)).thenReturn(Optional.of(cardType));
        when(cardVariantRepo.findById(2L)).thenReturn(Optional.of(cardVariant));
        when(cardNetworkRepo.findById(3L)).thenReturn(Optional.of(cardNetwork));
        when(networkBinRepo.findByCardNetwork(cardNetwork)).thenReturn(networkBin);
        when(reasonForRequestRepo.findById(4L)).thenReturn(Optional.of(reason));
        when(cardRequestsRepo.findByCustomersAndCardTypeAndRequestStatus(
                customer,
                cardType,
                RequestStatus.PENDING_REVIEW))
                .thenReturn(Optional.empty());

        CardRequests savedRequest = new CardRequests();
        savedRequest.setRequestId(100L);
        savedRequest.setCardType(cardType);
        savedRequest.setCardVariant(cardVariant);
        savedRequest.setCardNetwork(cardNetwork);
        savedRequest.setReason(reason);
        savedRequest.setNetworkBin(networkBin);
        savedRequest.setRequestStatus(RequestStatus.PENDING_REVIEW);
        savedRequest.setCustomers(customer);

        when(cardRequestsRepo.save(any(CardRequests.class))).thenReturn(savedRequest);
        when(notificationsServiceImpl.createNotifications(any())).thenReturn(null);
        ResponseDto response = cardRequestsServiceImpl.createRequest(dto);
        assertNotNull(response);
        assertEquals(100L, response.getCardRequestId());
        verify(cardRequestsRepo, times(1)).save(any(CardRequests.class));
        verify(notificationsServiceImpl, times(1)).createNotifications(any());
    }

    @Test
    void createRequest_userNotAuthenticated() {

        when(authentication.isAuthenticated()).thenReturn(false);
        RequestsDto dto = new RequestsDto();
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cardRequestsServiceImpl.createRequest(dto)
        );
        assertEquals("User not authenticated", ex.getMessage());
    }

    @Test
    void createRequest_customerNotFound() {

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test@gmail.com");

        RequestsDto dto = new RequestsDto();

        when(customersRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cardRequestsServiceImpl.createRequest(dto)
        );

        assertTrue(ex.getMessage().contains("Customer not found"));
    }

    @Test
    void createRequest_noBankAccount() {

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test@gmail.com");

        RequestsDto dto = new RequestsDto();

        Customers customer = new Customers();
        customer.setCustomerId(10L);

        when(customersRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(customer));
        when(accountCreationRepo.existsByCustomer(customer)).thenReturn(false);
        when(notificationsServiceImpl.createNotifications(any())).thenReturn(null);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cardRequestsServiceImpl.createRequest(dto)
        );

        assertEquals("Create a Bank Account first to continue!", ex.getMessage());

        verify(notificationsServiceImpl, times(1)).createNotifications(any());
    }
}