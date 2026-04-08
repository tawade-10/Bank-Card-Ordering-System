package com.example.bankingApp.controllers;

import com.example.bankingApp.dto.CardDto.CardRequestDto;
import com.example.bankingApp.dto.CardDto.CardResponseDto;
import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import com.example.bankingApp.facade.CardDetailsFacade.CardDetailsFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
