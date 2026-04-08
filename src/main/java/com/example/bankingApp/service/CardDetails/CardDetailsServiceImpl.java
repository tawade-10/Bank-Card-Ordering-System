package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import com.example.bankingApp.entity.CardDetails.CardDetails;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.RequestNewCard.CardType;
import com.example.bankingApp.entity.RequestNewCard.CardVariant;
import com.example.bankingApp.repository.card_details.CardDetailsRepo;
import com.example.bankingApp.repository.customer.CustomersRepo;
import com.example.bankingApp.repository.request_card.CardTypeRepo;
import com.example.bankingApp.repository.request_card.CardVariantRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CardDetailsServiceImpl implements CardDetailsService{

    private final CardTypeRepo cardTypeRepo;

    private final CardVariantRepo cardVariantRepo;

    private final CardDetailsRepo cardDetailsRepo;

    private final CustomersRepo customersRepo;

    public CardDetailsServiceImpl(CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, CardDetailsRepo cardDetailsRepo, CustomersRepo customersRepo) {
        this.cardTypeRepo = cardTypeRepo;
        this.cardVariantRepo = cardVariantRepo;
        this.cardDetailsRepo = cardDetailsRepo;
        this.customersRepo = customersRepo;
    }

    @Override
    public CardResponseDto createCard(CardRequestDto cardRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        CardType type = cardTypeRepo.findById(cardRequestDto.getCardType())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type ID"));

        CardVariant variant = cardVariantRepo.findById(cardRequestDto.getCardVariant())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant ID"));

        CardDetails cardDetails = new CardDetails();

        cardDetails.setCustomers(customer);
        cardDetails.setCardNumber(cardRequestDto.getCardNumber());
        cardDetails.setCardType(type);
        cardDetails.setCardVariant(variant);
        cardDetails.setExpiryDate(cardRequestDto.getExpiryDate());

        CardDetails savedCard = cardDetailsRepo.save(cardDetails);
        return new CardResponseDto(savedCard);
    }
}
