package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.facade.CardRequestsFacade.CardRequestsFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/request-card")
public class CardRequestsController {

    private final CardRequestsFacade cardRequestsFacade;

    public CardRequestsController(CardRequestsFacade cardRequestsFacade) {
        this.cardRequestsFacade = cardRequestsFacade;
    }

    @PostMapping("/create-request")
    public ResponseEntity<ResponseDto> createRequest(@Valid @RequestBody RequestsDto requestsDto){
        ResponseDto requestCreated = cardRequestsFacade.createRequest(requestsDto);
        return new ResponseEntity<>(requestCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllRequests(){
        List<ResponseDto> requests = cardRequestsFacade.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseDto> getRequestById(@PathVariable Long requestId){
        ResponseDto requestById = cardRequestsFacade.getRequestById(requestId);
        return ResponseEntity.ok(requestById);
    }

    @GetMapping("/email")
    public ResponseEntity<List<ResponseDto>> getRequestsByEmail(Authentication authentication){
        List<ResponseDto> requestByEmail = cardRequestsFacade.getRequestsByEmail(authentication);
        return ResponseEntity.ok(requestByEmail);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<ResponseDto> updateRequest(@PathVariable Long requestId,@RequestBody RequestsDto requestsDto){
        ResponseDto updatedRequest = cardRequestsFacade.updateRequest(requestId,requestsDto);
        return ResponseEntity.ok(updatedRequest);
    }

}
