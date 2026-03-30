package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;

import java.util.List;

public interface RequestsCardService {

    CardResponseDto createRequest(CardRequestsDto cardRequestsDto);

    List<CardResponseDto> getAllRequests();

    List<CardResponseDto> getRequestsByEmail(String email);
}
