package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.CardRequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CardResponseDto;
import com.example.bankingApp.entity.request_card.CardType;
import com.example.bankingApp.entity.request_card.CardVariant;
import com.example.bankingApp.entity.request_card.ReasonForRequest;
import com.example.bankingApp.entity.request_card.RequestCard;
import com.example.bankingApp.entity.customer.Customers;
import com.example.bankingApp.entity.enums.StatusOfRequest;
import com.example.bankingApp.repository.card.CardTypeRepo;
import com.example.bankingApp.repository.card.CardVariantRepo;
import com.example.bankingApp.repository.card.ReasonForRequestRepo;
import com.example.bankingApp.repository.customer.CustomersRepo;
import com.example.bankingApp.repository.card.RequestsCardRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestsCardServiceImpl implements RequestsCardService{

    private final RequestsCardRepo requestsCardRepo;

    private final CustomersRepo customersRepo;

    private final CardTypeRepo cardTypeRepo;

    private final CardVariantRepo cardVariantRepo;

    private final ReasonForRequestRepo reasonForRequestRepo;

    public RequestsCardServiceImpl(RequestsCardRepo requestsCardRepo, CustomersRepo customersRepo, CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, ReasonForRequestRepo reasonForRequestRepo) {
        this.requestsCardRepo = requestsCardRepo;
        this.customersRepo = customersRepo;
        this.cardTypeRepo = cardTypeRepo;
        this.cardVariantRepo = cardVariantRepo;
        this.reasonForRequestRepo = reasonForRequestRepo;
    }

    @Override
    public CardResponseDto createRequest(CardRequestsDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        CardType cardType = cardTypeRepo.findById(dto.getCardTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type ID"));

        CardVariant cardVariant = cardVariantRepo.findById(dto.getCardVariantId())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant ID"));

        ReasonForRequest reason = reasonForRequestRepo.findById(dto.getReasonId())
                .orElseThrow(() -> new RuntimeException("Invalid Reason ID"));

//        boolean exists = requestsCardRepo.hasPendingRequest(
//                customer, cardType, cardVariant, StatusOfRequest.PENDING_REVIEW
//        );
//
//        if (exists) {
//            throw new RuntimeException("A request for this card is already pending!");
//        }

        RequestCard request = new RequestCard();
        request.setCardType(cardType);
        request.setCardVariant(cardVariant);
        request.setReason(reason);
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
