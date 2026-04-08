package com.example.bankingApp.facade.CardDetailsFacade;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import jakarta.validation.Valid;

public interface CardDetailsFacade {
    CardResponseDto createCard(@Valid CardRequestDto cardRequestDto);
}
