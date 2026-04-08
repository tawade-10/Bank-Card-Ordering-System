package com.example.bankingApp.service.CardDetails;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;

public interface CardDetailsService {
    CardResponseDto createCard(CardRequestDto cardRequestDto);
}
