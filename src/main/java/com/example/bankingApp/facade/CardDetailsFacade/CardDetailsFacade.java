package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CardDetailsFacade {
    CardResponseDto createCard(@Valid CardRequestDto cardRequestDto);

    List<CardResponseDto> getAllCards();

    List<CardResponseDto> getCardsByCustomerId(Long customerId);

    List<CardResponseDto> getCardsByEmail(String email);
}
