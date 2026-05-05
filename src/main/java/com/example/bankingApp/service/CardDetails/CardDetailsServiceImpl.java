package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.CardRequests.NetworkBin;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.CardRequests.CardType;
import com.example.bankingApp.entity.CardRequests.CardVariant;
import com.example.bankingApp.repository.CardDetails.CardDetailsRepo;
import com.example.bankingApp.repository.CardRequests.CardRequestsRepo;
import com.example.bankingApp.repository.CardRequests.NetworkBinRepo;
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

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardDetailsResponseDto createCard(CardDetailsRequestDto dto) {

        CardRequests request = cardRequestsRepo.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Customers customer = request.getCustomers();
        CardType type = cardTypeRepo.findById(dto.getCardType())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type"));
        CardVariant variant = cardVariantRepo.findById(dto.getCardVariant())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant"));

        cardDetailsRepo.deactivatePreviousCard(customer.getCustomerId(), type.getTypeName());

        String rawCard = dto.getCardNumber();
        if (rawCard.length() != 16)
            throw new RuntimeException("Card number must be 16 digits");

        String last4 = rawCard.substring(12);
        String encrypted = encryptionService.encrypt(rawCard);

        if (dto.getCvv().length() != 3)
            throw new RuntimeException("CVV must be 3 digits");

        YearMonth expiry = YearMonth.parse(dto.getExpiry(), DateTimeFormatter.ofPattern("MM/yy"));
        if (expiry.isBefore(YearMonth.now()))
            throw new RuntimeException("Card expiry cannot be in the past");

        CardDetails card = new CardDetails();
        card.setCustomers(customer);
        card.setCardNumber(encrypted);
        card.setLast4(last4);
        card.setCardType(type);
        card.setCardVariant(variant);
        card.setNetworkBin(request.getNetworkBin());
        card.setExpiry(expiry);
        card.setActive(true);
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
    public List<CardDetailsResponseDto> getActiveCards(String email) {

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CardDetails> cards = cardDetailsRepo.findActiveCards(customer.getCustomerId());

        return cards.stream().map(CardDetailsResponseDto::new).toList();
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
