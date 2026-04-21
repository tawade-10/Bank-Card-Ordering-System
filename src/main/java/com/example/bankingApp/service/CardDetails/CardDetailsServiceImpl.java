package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
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
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    @Transactional
    public CardDetailsResponseDto createCard(CardDetailsRequestDto cardDetailsRequestDto) {

        CardRequests request = cardRequestsRepo.findById(cardDetailsRequestDto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Customers customer = request.getCustomers();

        CardType type = cardTypeRepo.findById(cardDetailsRequestDto.getCardType())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type ID"));

        CardVariant variant = cardVariantRepo.findById(cardDetailsRequestDto.getCardVariant())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant ID"));

        String rawCardNumber = cardDetailsRequestDto.getCardNumber();
        if (rawCardNumber.length() < 16)
            throw new RuntimeException("Card number must be 16 digits");

        String last4 = rawCardNumber.substring(rawCardNumber.length() - 4);

        String encryptedCard = encryptionService.encrypt(rawCardNumber);

        if (cardDetailsRequestDto.getCvv().length() < 3)
            throw new RuntimeException("Invalid CVV");

        YearMonth expiry = YearMonth.parse(cardDetailsRequestDto.getExpiry(), DateTimeFormatter.ofPattern("MM/yy"));

        CardDetails card = new CardDetails();
        card.setCustomers(customer);
        card.setCardNumber(encryptedCard);
        card.setLast4(last4);
        card.setCardType(type);
        card.setCardVariant(variant);
        card.setExpiry(expiry);
        card.setCreatedAt(LocalDateTime.now());

        CardDetails saved = cardDetailsRepo.save(card);

        return new CardDetailsResponseDto(saved);
    }

    @Override
    public List<CardDetailsResponseDto> getAllCards() {
        List<CardDetails> cards = cardDetailsRepo.findAll();
        return cards.stream().map(CardDetailsResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CardDetailsResponseDto> getCardsByCustomerId(Long customerId) {

        List<CardDetails> cards = cardDetailsRepo.findByCustomersCustomerId(customerId);

        if (cards.isEmpty()) {
            throw new RuntimeException("No cards found for customer ID: " + customerId);
        }

        return cards.stream().map(CardDetailsResponseDto::new).toList();
    }

    public List<CardDetailsResponseDto> getCardsByEmail(String email) {
        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CardDetails> cards = cardDetailsRepo.findByCustomers(customer);

        return cards.stream().map(CardDetailsResponseDto::new).toList();
    }
}
