package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import com.example.bankingApp.service.RequestsCard.RequestsCardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestsCardFacadeImpl implements RequestsCardFacade{

    private final RequestsCardService requestsCardService;

    public RequestsCardFacadeImpl(RequestsCardService requestsCardService) {
        this.requestsCardService = requestsCardService;
    }

    @Override
    public ResponseDto createRequest(RequestsDto requestsDto) {
        return requestsCardService.createRequest(requestsDto);
    }

    @Override
    public List<ResponseDto> getAllRequests() {
        return requestsCardService.getAllRequests();
    }

    @Override
    public ResponseDto getRequestById(Long requestId) { return requestsCardService.getRequestById(requestId);
    }

    @Override
    public List<ResponseDto> getRequestsByEmail(Authentication authentication) {
        return requestsCardService.getRequestsByEmail(authentication);
    }

    @Override
    public ResponseDto updateRequest(Long requestId, RequestsDto requestsDto) {
        return requestsCardService.updateRequest(requestId,requestsDto);
    }
}
