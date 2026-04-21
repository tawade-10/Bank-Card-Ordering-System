package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardDetailsDto.CardDetailsRequestDto;
import com.example.bankingApp.dto.CardDetailsDto.CardDetailsResponseDto;
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
    public ResponseEntity<CardDetailsResponseDto> createCard(@Valid @RequestBody CardDetailsRequestDto cardDetailsRequestDto){
        CardDetailsResponseDto cardCreated = cardDetailsFacade.createCard(cardDetailsRequestDto);
        return new ResponseEntity<>(cardCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CardDetailsResponseDto>> getAllCards(){
        List<CardDetailsResponseDto> allCards = cardDetailsFacade.getAllCards();
        return ResponseEntity.ok(allCards);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CardDetailsResponseDto>> getCardsByCustomerId(@PathVariable Long customerId){
        List<CardDetailsResponseDto> cardById = cardDetailsFacade.getCardsByCustomerId(customerId);
        return ResponseEntity.ok(cardById);
    }

    @GetMapping("/my-cards")
    public ResponseEntity<List<CardDetailsResponseDto>> getMyCards(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<CardDetailsResponseDto> cards = cardDetailsFacade.getCardsByEmail(email);
        return ResponseEntity.ok(cards);
    }

}
