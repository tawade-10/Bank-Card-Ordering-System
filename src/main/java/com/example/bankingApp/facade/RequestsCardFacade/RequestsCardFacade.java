package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface RequestsCardFacade {
    CardResponseDto createRequest(@Valid CardRequestsDto cardRequestsDto);

    List<CardResponseDto> getAllRequests();

    List<CardResponseDto> getRequestsByEmail(String email);
}
