package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardRequests.*;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.repository.CardRequests.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.CardDetails.CardDetailsServiceImpl;
import com.example.bankingApp.service.Encryption.EncryptionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardDetailsServiceImplTest {

    @Mock
    private CardRequestsRepo cardRequestsRepo;

    @Mock
    private CardTypeRepo cardTypeRepo;

    @Mock
    private CardVariantRepo cardVariantRepo;

    @Mock
    private CardDetailsRepo cardDetailsRepo;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private CardDetailsServiceImpl cardDetailsService;

    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setup() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createCard_Success() {

        CardDetailsRequestDto dto = new CardDetailsRequestDto();
        dto.setRequestId(1L);
        dto.setCardType(2L);
        dto.setCardVariant(3L);
        dto.setCardNumber("6483573593522541");
        dto.setExpiry("03/30");
        dto.setCvv("624");

        Customers customer = new Customers();
        customer.setCustomerId(10L);

        CardRequests request = new CardRequests();
        request.setRequestId(1L);
        request.setCustomers(customer);
        request.setNetworkBin(new NetworkBin());

        CardType type = new CardType();
        type.setTypeName("DEBIT");

        CardVariant variant = new CardVariant();
        variant.setVariantName("PLATINUM");

        CardDetails saved = new CardDetails();
        saved.setCardId(100L);
        saved.setCustomers(customer);
        saved.setCardType(type);
        saved.setCardVariant(variant);
        saved.setCardNumber("ENCRYPTED_CARD");
        saved.setLast4("2541");
        saved.setExpiry(YearMonth.of(2030, 3));
        saved.setActive(true);

        // MOCKS
        when(cardRequestsRepo.findById(1L)).thenReturn(Optional.of(request));
        when(cardTypeRepo.findById(2L)).thenReturn(Optional.of(type));
        when(cardVariantRepo.findById(3L)).thenReturn(Optional.of(variant));
        when(encryptionService.encrypt("6483573593522541")).thenReturn("ENCRYPTED_CARD");
        when(cardDetailsRepo.save(any(CardDetails.class))).thenReturn(saved);

        // WHEN
        CardDetailsResponseDto response = cardDetailsService.createCard(dto);

        // THEN
        assertNotNull(response);
        assertEquals(100L, response.getCardId());
        assertEquals("DEBIT", response.getCardType());
        assertEquals("PLATINUM", response.getCardVariant());

        verify(cardRequestsRepo).findById(1L);
        verify(cardTypeRepo).findById(2L);
        verify(cardVariantRepo).findById(3L);
        verify(encryptionService).encrypt("6483573593522541");
        verify(cardDetailsRepo).save(any(CardDetails.class));
        verify(cardRequestsRepo).save(any(CardRequests.class));
    }
}