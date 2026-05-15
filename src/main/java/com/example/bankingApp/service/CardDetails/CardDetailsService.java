package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardsDto.CardsResponseDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import com.example.bankingApp.dto.CardVariantsDto.CardVariantsResponseDto;
import com.example.bankingApp.dto.CardsStatusSummaryResponse.CardsStatusSummaryResponse;

import java.util.List;

public interface CardDetailsService {
    CardDetailsResponseDto createCard(CardDetailsRequestDto cardDetailsRequestDto);

    List<CardDetailsResponseDto> getAllCards();

    List<CardDetailsResponseDto> getCardsByCustomerId(Long customerId);

    List<CardDetailsResponseDto> getCardsByEmail(String email);

    List<CardsResponseDto> getActiveCards(String email);

    CardVariantsResponseDto getVariantById(Long variantId);

    CardsStatusSummaryResponse getCardsByStatus();

    List<CardsResponseDto> getInactiveCards(String email);
}
