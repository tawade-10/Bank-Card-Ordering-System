package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;

public interface RequestsCardService {

    CardResponseDto createRequest(CardRequestsDto cardRequestsDto);
}
