package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
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
    public CardResponseDto createCard(CardRequestDto cardRequestDto) {
        return cardDetailsService.createCard(cardRequestDto);
    }

    @Override
    public List<CardResponseDto> getAllCards() {
        return cardDetailsService.getAllCards();
    }

    @Override
    public CardResponseDto getCardById(Long cardId) {
        return cardDetailsService.getCardById(cardId);
    }
}
