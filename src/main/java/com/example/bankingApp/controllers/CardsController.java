package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import com.example.bankingApp.facade.CardDetailsFacade.CardDetailsFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cards")
public class CardsController {

    private final CardDetailsFacade cardDetailsFacade;

    public CardsController(CardDetailsFacade cardDetailsFacade) {
        this.cardDetailsFacade = cardDetailsFacade;
    }

    @PostMapping("/create-card")
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CardRequestDto cardRequestDto){
        CardResponseDto cardCreated = cardDetailsFacade.createCard(cardRequestDto);
        return new ResponseEntity<>(cardCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCards(){
        List<CardResponseDto> allCards = cardDetailsFacade.getAllCards();
        return ResponseEntity.ok(allCards);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CardResponseDto>> getCardsByCustomerId(@PathVariable Long customerId){
        List<CardResponseDto> cardById = cardDetailsFacade.getCardsByCustomerId(customerId);
        return ResponseEntity.ok(cardById);
    }

    @GetMapping("/my-cards")
    public ResponseEntity<List<CardResponseDto>> getMyCards(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<CardResponseDto> cards = cardDetailsFacade.getCardsByEmail(email);
        return ResponseEntity.ok(cards);
    }

}
