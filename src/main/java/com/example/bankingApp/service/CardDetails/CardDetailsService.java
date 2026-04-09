package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;

import java.util.List;

public interface CardDetailsService {
    CardResponseDto createCard(CardRequestDto cardRequestDto);

    List<CardResponseDto> getAllCards();

    CardResponseDto getCardById(Long cardId);
}
