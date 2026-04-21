package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CardDetailsFacade {
    CardDetailsResponseDto createCard(@Valid CardDetailsRequestDto cardDetailsRequestDto);

    List<CardDetailsResponseDto> getAllCards();

    List<CardDetailsResponseDto> getCardsByCustomerId(Long customerId);

    List<CardDetailsResponseDto> getCardsByEmail(String email);
}
