package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.service.CardDetails.CardDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<CardDetailsResponseDto> getActiveCards(String email) {
        return cardDetailsService.getActiveCards(email);
    }

    @Override
    public List<CardDetailsResponseDto> getCardsByEmail(String email) {
        return cardDetailsService.getCardsByEmail(email);
    }
}
