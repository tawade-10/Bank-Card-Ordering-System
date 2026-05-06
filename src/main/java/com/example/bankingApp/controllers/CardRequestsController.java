package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.dto.CardVariantsDto.CardVariantsResponseDto;
import com.example.bankingApp.dto.NetworkDto.NetworkResponseDto;
import com.example.bankingApp.dto.ReviewDto.ReviewRequestsDto;
import com.example.bankingApp.dto.ReviewDto.ReviewResponseDto;
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

    @GetMapping("/network/{networkId}")
    public ResponseEntity<NetworkResponseDto> getBinByNetwork(@PathVariable Long networkId){
        NetworkResponseDto network = cardRequestsFacade.getBinByNetwork(networkId);
        return ResponseEntity.ok(network);
    }

    @PutMapping("/{requestId}/review")
    public ResponseEntity<ReviewResponseDto> reviewRequest(@PathVariable Long requestId, @RequestBody ReviewRequestsDto reviewRequestsDto) {
        ReviewResponseDto updatedRequest = cardRequestsFacade.reviewRequest(requestId, reviewRequestsDto);
        return ResponseEntity.ok(updatedRequest);
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<ReviewResponseDto> updateRequestStatus(@PathVariable Long requestId, @RequestBody ReviewRequestsDto reviewRequestsDto) {
        ReviewResponseDto updatedRequest = cardRequestsFacade.updateRequestStatus(requestId, reviewRequestsDto);
        return ResponseEntity.ok(updatedRequest);
    }
}
