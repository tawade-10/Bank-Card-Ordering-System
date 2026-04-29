package com.example.bankingApp.facade.CardRequestsFacade;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.service.CardRequests.CardRequestsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardRequestsFacadeImpl implements CardRequestsFacade {

    private final CardRequestsService cardRequestsService;

    public CardRequestsFacadeImpl(CardRequestsService cardRequestsService) {
        this.cardRequestsService = cardRequestsService;
    }

    @Override
    public ResponseDto createRequest(RequestsDto requestsDto) {
        return cardRequestsService.createRequest(requestsDto);
    }

    @Override
    public List<ResponseDto> getAllRequests() {
        return cardRequestsService.getAllRequests();
    }

    @Override
    public ResponseDto getRequestById(Long requestId) { return cardRequestsService.getRequestById(requestId);
    }

    @Override
    public List<ResponseDto> getRequestsByEmail(Authentication authentication) {
        return cardRequestsService.getRequestsByEmail(authentication);
    }

    @Override
    public ResponseDto getBinByNetwork(Long networkId) {
        return cardRequestsService.getBinByNetwork(networkId);
    }

    @Override
    public ResponseDto reviewRequest(Long requestId, RequestsDto requestsDto) {
        return cardRequestsService.reviewRequest(requestId,requestsDto);
    }

    @Override
    public ResponseDto updateRequestStatus(Long requestId, RequestsDto requestsDto) {
        return cardRequestsService.updateRequestStatus(requestId,requestsDto);
    }
}
