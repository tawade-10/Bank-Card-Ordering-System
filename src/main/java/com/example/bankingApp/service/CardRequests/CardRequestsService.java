package com.example.bankingApp.service.CardRequests;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardRequestsService {

    ResponseDto createRequest(RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    List<ResponseDto> getRequestsByEmail(Authentication authentication);

    ResponseDto reviewRequest(Long requestId, RequestsDto requestsDto);

    ResponseDto updateRequestStatus(Long requestId, RequestsDto requestsDto);
}
