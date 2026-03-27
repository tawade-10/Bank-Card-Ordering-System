package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import jakarta.validation.Valid;

public interface RequestsCardFacade {
    CardResponseDto createRequest(@Valid CardRequestsDto cardRequestsDto);
}
