package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.service.RequestsCard.RequestsCardService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestsCardFacadeImpl implements RequestsCardFacade{

    private final RequestsCardService requestsCardService;

    public RequestsCardFacadeImpl(RequestsCardService requestsCardService) {
        this.requestsCardService = requestsCardService;
    }

    @Override
    public CardResponseDto createRequest(CardRequestsDto cardRequestsDto) {
        return requestsCardService.createRequest(cardRequestsDto);
    }

    @Override
    public List<CardResponseDto> getAllRequests() {
        return requestsCardService.getAllRequests();
    }

    @Override
    public List<CardResponseDto> getRequestsByEmail(String email) {
        return requestsCardService.getRequestsByEmail(email);
    }
}
