package com.example.bankingApp.facade.RequestsCardFacade;

import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface RequestsCardFacade {
    ResponseDto createRequest(@Valid RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);
}
