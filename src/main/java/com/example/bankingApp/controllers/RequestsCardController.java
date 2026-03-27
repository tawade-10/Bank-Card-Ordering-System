package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.facade.RequestsCardFacade.RequestsCardFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/request-card")
public class RequestsCardController {

    private final RequestsCardFacade requestsCardFacade;

    public RequestsCardController(RequestsCardFacade requestsCardFacade) {
        this.requestsCardFacade = requestsCardFacade;
    }

    @PostMapping("/create-request")
    public ResponseEntity<CardResponseDto> createRequest(@Valid @RequestBody CardRequestsDto cardRequestsDto){
        CardResponseDto requestCreated = requestsCardFacade.createRequest(cardRequestsDto);
        return new ResponseEntity<>(requestCreated, HttpStatus.CREATED);
    }

}
