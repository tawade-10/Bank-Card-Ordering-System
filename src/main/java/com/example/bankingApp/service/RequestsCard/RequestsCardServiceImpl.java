package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.entity.RequestCard;
import com.example.bankingApp.entity.enums.StatusOfRequest;
import com.example.bankingApp.repository.CustomersRepo;
import com.example.bankingApp.repository.RequestsCardRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestsCardServiceImpl implements RequestsCardService{

    private final RequestsCardRepo requestsCardRepo;

    private final CustomersRepo customersRepo;

    public RequestsCardServiceImpl(RequestsCardRepo requestsCardRepo, CustomersRepo customersRepo) {
        this.requestsCardRepo = requestsCardRepo;
        this.customersRepo = customersRepo;
    }

    @Override
    public CardResponseDto createRequest(CardRequestsDto cardRequestsDto) {

        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        Customers customer = customersRepo.findByEmail(email);
//                .orElseThrow(() -> new RuntimeException("Customer not found"));

        RequestCard request = new RequestCard();

        request.setCardType(cardRequestsDto.getCardType());
        request.setCardVariant(cardRequestsDto.getCardVariant());
        request.setReason(cardRequestsDto.getReason());
        request.setStatusOfRequest(StatusOfRequest.PENDING_REVIEW);
        request.setLocalDate(LocalDate.now());
        request.setCustomer(customer);

        RequestCard savedRequest = requestsCardRepo.save(request);
        return new CardResponseDto(savedRequest);
    }

    @Override
    public List<CardResponseDto> getAllRequests() {
        List<RequestCard> requests = requestsCardRepo.findAll();
        return requests.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CardResponseDto> getRequestsByEmail(String email) {
        List<RequestCard> requests = requestsCardRepo.findByCustomerEmail(email);
        return requests.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }
}
