package com.example.bankingApp.facade.CardRequestsFacade;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.dto.NetworkDto.NetworkResponseDto;
import com.example.bankingApp.dto.ReviewDto.ReviewRequestsDto;
import com.example.bankingApp.dto.ReviewDto.ReviewResponseDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardRequestsFacade {
    ResponseDto createRequest(@Valid RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    List<ResponseDto> getRequestsByEmail(Authentication authentication);

    ReviewResponseDto reviewRequest(Long requestId, ReviewRequestsDto reviewRequestsDto);

    ReviewResponseDto updateRequestStatus(Long requestId, ReviewRequestsDto reviewRequestsDto);

    NetworkResponseDto getBinByNetwork(Long networkId);
}
