package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.CardRequests.CardType;
import com.example.bankingApp.entity.CardRequests.CardVariant;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.repository.CardRequests.CardRequestsRepo;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.repository.CardRequests.CardTypeRepo;
import com.example.bankingApp.repository.CardRequests.CardVariantRepo;
import com.example.bankingApp.service.Encryption.EncryptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardDetailsServiceImpl implements CardDetailsService{

    private final CardTypeRepo cardTypeRepo;

    private final CardVariantRepo cardVariantRepo;

    private final CardDetailsRepo cardDetailsRepo;

    private final CustomersRepo customersRepo;

    private final CardRequestsRepo cardRequestsRepo;

    private final EncryptionService encryptionService;

    public CardDetailsServiceImpl(CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, CardDetailsRepo cardDetailsRepo, CustomersRepo customersRepo, CardRequestsRepo cardRequestsRepo, EncryptionService encryptionService) {
        this.cardTypeRepo = cardTypeRepo;
        this.cardVariantRepo = cardVariantRepo;
        this.cardDetailsRepo = cardDetailsRepo;
        this.customersRepo = customersRepo;
        this.cardRequestsRepo = cardRequestsRepo;
        this.encryptionService = encryptionService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public CardResponseDto createCard(CardRequestDto cardRequestDto) {

        Long requestId = cardRequestDto.getRequestId();

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Customers customer = request.getCustomers();

        CardType type = cardTypeRepo.findById(cardRequestDto.getCardType())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type ID"));

        CardVariant variant = cardVariantRepo.findById(cardRequestDto.getCardVariant())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant ID"));

        CardDetails cardDetails = new CardDetails();

        cardDetails.setCustomers(customer);
        cardDetails.setCardNumber(encryptionService.encrypt(String.valueOf(cardRequestDto.getCardNumber())));
        cardDetails.setCvv(encryptionService.encrypt(String.valueOf(cardRequestDto.getCvv())));
        cardDetails.setCardType(type);
        cardDetails.setCardVariant(variant);
        cardDetails.setExpiryDate(cardRequestDto.getExpiryDate());

        CardDetails savedCard = cardDetailsRepo.save(cardDetails);

        return new CardResponseDto(savedCard);
    }

    @Override
    public List<CardResponseDto> getAllCards() {
        List<CardDetails> cards = cardDetailsRepo.findAll();
        return cards.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CardResponseDto> getCardsByCustomerId(Long customerId) {

        List<CardDetails> cards = cardDetailsRepo.findByCustomersCustomerId(customerId);

        if (cards.isEmpty()) {
            throw new RuntimeException("No cards found for customer ID: " + customerId);
        }

        return cards.stream().map(CardResponseDto::new).toList();
    }

    public List<CardResponseDto> getCardsByEmail(String email) {
        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CardDetails> cards = cardDetailsRepo.findByCustomers(customer);

        return cards.stream().map(CardResponseDto::new).toList();
    }
}
