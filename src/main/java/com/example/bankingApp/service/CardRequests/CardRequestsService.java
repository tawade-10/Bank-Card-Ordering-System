package com.example.bankingApp.service.CardRequests;

import com.example.bankingApp.dto.CardRequestsDto.CreationDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CreationDto.ResponseDto;
import com.example.bankingApp.dto.NetworkDto.NetworkResponseDto;
import com.example.bankingApp.dto.ReviewDto.ReviewRequestsDto;
import com.example.bankingApp.dto.ReviewDto.ReviewResponseDto;

import java.util.List;

public interface CardRequestsService {

    ResponseDto createRequest(RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    List<ResponseDto> getRequestsByEmail();

    ReviewResponseDto reviewRequest(Long requestId, ReviewRequestsDto reviewRequestsDto);

    ReviewResponseDto updateRequestStatus(Long requestId, ReviewRequestsDto reviewRequestsDto);

    NetworkResponseDto getBinByNetwork(Long networkId);
}
