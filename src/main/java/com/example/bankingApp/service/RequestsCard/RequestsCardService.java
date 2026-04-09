package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;

import java.util.List;

public interface RequestsCardService {

    ResponseDto createRequest(RequestsDto requestsDto);

    List<ResponseDto> getAllRequests();

    ResponseDto getRequestById(Long requestId);

    ResponseDto updateRequest(Long requestId, RequestsDto requestsDto);
}
