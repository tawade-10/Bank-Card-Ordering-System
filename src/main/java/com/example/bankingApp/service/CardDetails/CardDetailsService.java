package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.ActiveCardsDto.ActiveCardsResponseDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.dto.CardVariantsDto.CardVariantsResponseDto;
import com.example.bankingApp.dto.CardsStatusSummaryResponse.CardsStatusSummaryResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CardDetailsService {
    CardDetailsResponseDto createCard(CardDetailsRequestDto cardDetailsRequestDto);

    List<CardDetailsResponseDto> getAllCards();

    List<CardDetailsResponseDto> getCardsByCustomerId(Long customerId);

    List<CardDetailsResponseDto> getCardsByEmail(String email);

    List<ActiveCardsResponseDto> getActiveCards(String email);

    CardVariantsResponseDto getVariantById(Long variantId);

    CardsStatusSummaryResponse getCardsByStatus();
}
