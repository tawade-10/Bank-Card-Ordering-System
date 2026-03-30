package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.entity.Customers;
import com.example.bankingApp.entity.RequestCard;
import com.example.bankingApp.entity.enums.StatusOfRequest;
import com.example.bankingApp.repository.CustomersRepo;
import com.example.bankingApp.repository.RequestsCardRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    public CardResponseDto createRequest(CardRequestsDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();
        Customers customer = customersRepo.findByEmail(email);

        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + email);
        }

        RequestCard request = new RequestCard();
        request.setCardType(dto.getCardType());
        request.setCardVariant(dto.getCardVariant());
        request.setReason(dto.getReason());
        request.setStatusOfRequest(StatusOfRequest.PENDING_REVIEW);
        request.setLocalDate(LocalDate.now());
        request.setCustomer(customer);

        RequestCard saved = requestsCardRepo.save(request);

        return new CardResponseDto(saved);
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
