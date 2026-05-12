package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.ActiveCardsDto.ActiveCardsResponseDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.dto.CardVariantsDto.CardVariantsResponseDto;
import com.example.bankingApp.dto.CardsStatusSummaryResponse.CardsStatusSummaryResponse;
import com.example.bankingApp.service.CardDetails.CardDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardDetailsFacadeImpl implements CardDetailsFacade{

    private final CardDetailsService cardDetailsService;

    public CardDetailsFacadeImpl(CardDetailsService cardDetailsService) {
        this.cardDetailsService = cardDetailsService;
    }

    @Override
    public CardDetailsResponseDto createCard(CardDetailsRequestDto cardDetailsRequestDto) {
        return cardDetailsService.createCard(cardDetailsRequestDto);
    }

    @Override
    public List<CardDetailsResponseDto> getAllCards() {
        return cardDetailsService.getAllCards();
    }

    @Override
    public List<CardDetailsResponseDto> getCardsByCustomerId(Long customerId) {
        return cardDetailsService.getCardsByCustomerId(customerId);
    }

    @Override
    public List<ActiveCardsResponseDto> getActiveCards(String email) {
        return cardDetailsService.getActiveCards(email);
    }

    @Override
    public CardVariantsResponseDto getVariantById(Long variantId) {
        return cardDetailsService.getVariantById(variantId);
    }

    @Override
    public CardsStatusSummaryResponse getCardsByStatus() {
        return cardDetailsService.getCardsByStatus();
    }

    @Override
    public List<CardDetailsResponseDto> getCardsByEmail(String email) {
        return cardDetailsService.getCardsByEmail(email);
    }
}
