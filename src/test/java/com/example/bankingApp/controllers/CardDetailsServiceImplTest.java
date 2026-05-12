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
    private CustomersRepo customersRepo;

    @Mock
    private CardTypeRepo cardTypeRepo;

    @Mock
    private CardVariantRepo cardVariantRepo;

    @Mock
    private CardDetailsRepo cardDetailsRepo;

    @Mock
    private ReasonForRequestRepo reasonForRequestRepo;

    @Mock
    private NetworkBinRepo networkBinRepo;

    @Mock
    private CardNetworkRepo cardNetworkRepo;

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
        dto.setCardNumber("6483573593522541");  // 16 digits
        dto.setExpiry("03/30");
        dto.setCvv("624");

        // --- Mock Customer ---
        Customers customer = new Customers();
        customer.setCustomerId(10L);
        customer.setCustomerName("Shubham");

        // --- Mock NetworkBin ---
        CardNetwork network = new CardNetwork();
        network.setNetworkId(99L);
        network.setNetworkName("VISA");

        NetworkBin bin = new NetworkBin();
        bin.setBinId(5L);
        bin.setBinNumber("648357");
        bin.setCardNetwork(network);

        // --- Mock CardRequest ---
        CardRequests request = new CardRequests();
        request.setRequestId(1L);
        request.setCustomers(customer);
        request.setNetworkBin(bin);

        when(cardRequestsRepo.findById(1L)).thenReturn(Optional.of(request));

        // --- Mock CardType ---
        CardType type = new CardType();
        type.setTypeId(2L);
        type.setTypeName("DEBIT");

        when(cardTypeRepo.findById(2L)).thenReturn(Optional.of(type));

        // --- Mock CardVariant ---
        CardVariant variant = new CardVariant();
        variant.setVariantId(3L);
        variant.setVariantName("PLATINUM");

        when(cardVariantRepo.findById(3L)).thenReturn(Optional.of(variant));

        // --- Encryption ---
        when(encryptionService.encrypt("6483573593522541"))
                .thenReturn("ENCRYPTED_CARD");

        // deactivate
        doNothing().when(cardDetailsRepo)
                .deactivatePreviousCard(10L, "DEBIT");

        // --- Mock saved entity ---
        CardDetails saved = new CardDetails();
        saved.setCardId(100L);
        saved.setCustomers(customer);
        saved.setCardType(type);
        saved.setCardVariant(variant);
        saved.setNetworkBin(bin);
        saved.setCardNumber("ENCRYPTED_CARD");
        saved.setLast4("2541");
        saved.setExpiry(YearMonth.of(2030, 3));
        saved.setActive(true);

        when(cardDetailsRepo.save(any(CardDetails.class))).thenReturn(saved);

        // --- CALL SERVICE ---
        CardDetailsResponseDto response = cardDetailsService.createCard(dto);

        // --- Assertions ---
        assertNotNull(response);
        assertEquals(100L, response.getCardId());
        assertEquals("DEBIT", response.getCardType());
        assertEquals("PLATINUM", response.getCardVariant());
        assertEquals("648357** **** **** 2541", response.getMaskedNumber());
        assertEquals("03/30", response.getExpiry());

        // Verify
        verify(cardRequestsRepo, times(1)).findById(1L);
        verify(cardTypeRepo, times(1)).findById(2L);
        verify(cardVariantRepo, times(1)).findById(3L);
        verify(encryptionService).encrypt("6483573593522541");
        verify(cardDetailsRepo).deactivatePreviousCard(10L,"DEBIT");
        verify(cardDetailsRepo).save(any(CardDetails.class));
        verify(cardRequestsRepo).save(any(CardRequests.class));
    }
}