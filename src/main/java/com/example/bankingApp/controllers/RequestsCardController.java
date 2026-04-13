package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CustomersDto.CustomersRequestDto;
import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import com.example.bankingApp.facade.RequestsCardFacade.RequestsCardFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/request-card")
public class RequestsCardController {

    private final RequestsCardFacade requestsCardFacade;

    public RequestsCardController(RequestsCardFacade requestsCardFacade) {
        this.requestsCardFacade = requestsCardFacade;
    }

    @PostMapping("/create-request")
    public ResponseEntity<ResponseDto> createRequest(@Valid @RequestBody RequestsDto requestsDto){
        ResponseDto requestCreated = requestsCardFacade.createRequest(requestsDto);
        return new ResponseEntity<>(requestCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllRequests(){
        List<ResponseDto> requests = requestsCardFacade.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseDto> getRequestById(@PathVariable Long requestId){
        ResponseDto requestById = requestsCardFacade.getRequestById(requestId);
        return ResponseEntity.ok(requestById);
    }

    @GetMapping("/email")
    public ResponseEntity<List<ResponseDto>> getRequestsByEmail(Authentication authentication){
        List<ResponseDto> requestByEmail = requestsCardFacade.getRequestsByEmail(authentication);
        return ResponseEntity.ok(requestByEmail);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<ResponseDto> updateRequest(@PathVariable Long requestId,@RequestBody RequestsDto requestsDto){
        ResponseDto updatedRequest = requestsCardFacade.updateRequest(requestId,requestsDto);
        return ResponseEntity.ok(updatedRequest);
    }

}
