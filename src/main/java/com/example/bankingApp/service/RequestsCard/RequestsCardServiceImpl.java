package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.entity.RequestCard;
import com.example.bankingApp.repository.RequestsCardRepo;
import org.springframework.stereotype.Component;

@Component
public class RequestsCardServiceImpl implements RequestsCardService{

    private final RequestsCardRepo requestsCardRepo;

    public RequestsCardServiceImpl(RequestsCardRepo requestsCardRepo) {
        this.requestsCardRepo = requestsCardRepo;
    }

    @Override
    public CardResponseDto createRequest(CardRequestsDto cardRequestsDto) {

        RequestCard request = new RequestCard();

        request.setCardType(cardRequestsDto.getCardType());
        request.setCardVariant(cardRequestsDto.getCardVariant());
        request.setReason(cardRequestsDto.getReason());

        RequestCard savedRequest = requestsCardRepo.save(request);
        return new CardResponseDto(savedRequest);
    }
}
