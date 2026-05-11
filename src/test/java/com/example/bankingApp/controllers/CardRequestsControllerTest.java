package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.entity.CardRequests.*;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.repository.CardRequests.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.CardRequests.CardRequestsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
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
    private ReasonForRequestRepo reasonForRequestRepo;

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
        CardType cardType = new CardType();
        CardVariant cardVariant = new CardVariant();
        CardNetwork cardNetwork = new CardNetwork();
        NetworkBin networkBin = new NetworkBin();
        Reason reason = new Reason();

        when(customersRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(customer));
        when(cardTypeRepo.findById(1L)).thenReturn(Optional.of(cardType));
        when(cardVariantRepo.findById(2L)).thenReturn(Optional.of(cardVariant));
        when(cardNetworkRepo.findById(3L)).thenReturn(Optional.of(cardNetwork));
        when(networkBinRepo.findByCardNetwork(cardNetwork)).thenReturn(networkBin);
        when(reasonForRequestRepo.findById(4L)).thenReturn(Optional.of(reason));

        // ---------- Mock Save ----------
        CardRequests saved = new CardRequests();
        saved.setRequestId(100L);
        saved.setCardType(cardType);
        saved.setCardVariant(cardVariant);
        saved.setReason(reason);
        saved.setCardNetwork(cardNetwork);
        saved.setNetworkBin(networkBin);
        saved.setStatus(Status.PENDING_REVIEW);
        saved.setLocalDate(LocalDate.now());
        saved.setCustomers(customer);

        when(cardRequestsRepo.save(any(CardRequests.class))).thenReturn(saved);

        ResponseDto responseDto = cardRequestsServiceImpl.createRequest(dto);

        assertNotNull(responseDto);
        assertEquals(100L, responseDto.getRequestId());
        verify(cardRequestsRepo, times(1)).save(any(CardRequests.class));
    }

    @Test
    void createRequest_NullRequest_ThrowsException() {

        RequestsDto dto = new RequestsDto();
        dto.setCardTypeId(null);

        assertThrows(RuntimeException.class, () ->{
            cardRequestsServiceImpl.createRequest(dto);
        });
    }

//    @Test
//    void getAllRequestsSuccess(){
//        CardRequests request1 = new CardRequests();
//        request1.setCardType();
//        request1.setCardVariantId(2L);
//        request1.setCardNetworkId(3L);
//        request1.setReasonId(4L);
//
//    }

}