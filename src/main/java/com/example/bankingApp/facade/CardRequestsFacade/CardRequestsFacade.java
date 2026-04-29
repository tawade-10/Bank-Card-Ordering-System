package com.example.bankingApp.facade.CardRequestsFacade;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardRequestsFacade {
    ResponseDto createRequest(@Valid RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    List<ResponseDto> getRequestsByEmail(Authentication authentication);

    ResponseDto reviewRequest(Long requestId, RequestsDto requestsDto);

    ResponseDto updateRequestStatus(Long requestId, RequestsDto requestsDto);

    ResponseDto getBinByNetwork(Long networkId);
}
