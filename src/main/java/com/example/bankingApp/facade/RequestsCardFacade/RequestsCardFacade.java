package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RequestsCardFacade {
    ResponseDto createRequest(@Valid RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    List<ResponseDto> getRequestsByEmail(Authentication authentication);

    ResponseDto updateRequest(Long requestId, RequestsDto requestsDto);
}
