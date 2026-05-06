package com.example.bankingApp.facade.CardRequestsFacade;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.dto.NetworkDto.NetworkResponseDto;
import com.example.bankingApp.dto.ReviewDto.ReviewRequestsDto;
import com.example.bankingApp.dto.ReviewDto.ReviewResponseDto;
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
    public NetworkResponseDto getBinByNetwork(Long networkId) {
        return cardRequestsService.getBinByNetwork(networkId);
    }

    @Override
    public ReviewResponseDto reviewRequest(Long requestId, ReviewRequestsDto reviewRequestsDto) {
        return cardRequestsService.reviewRequest(requestId,reviewRequestsDto);
    }

    @Override
    public ReviewResponseDto updateRequestStatus(Long requestId, ReviewRequestsDto reviewRequestsDto) {
        return cardRequestsService.updateRequestStatus(requestId,reviewRequestsDto);
    }
}
