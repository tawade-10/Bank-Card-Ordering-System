package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CustomersDto.CustomersResponseDto;
import com.example.bankingApp.dto.RequestCardDto.RequestsDto;
import com.example.bankingApp.dto.RequestCardDto.ResponseDto;
import com.example.bankingApp.entity.RequestNewCard.CardType;
import com.example.bankingApp.entity.RequestNewCard.CardVariant;
import com.example.bankingApp.entity.RequestNewCard.Reason;
import com.example.bankingApp.entity.RequestNewCard.RequestNewCard;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.StatusOfRequest;
import com.example.bankingApp.repository.request_card.CardTypeRepo;
import com.example.bankingApp.repository.request_card.CardVariantRepo;
import com.example.bankingApp.repository.request_card.ReasonForRequestRepo;
import com.example.bankingApp.repository.customer.CustomersRepo;
import com.example.bankingApp.repository.request_card.RequestsCardRepo;
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
    public ResponseDto createRequest(RequestsDto dto) {

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

        Reason reason = reasonForRequestRepo.findById(dto.getReasonId())
                .orElseThrow(() -> new RuntimeException("Invalid Reason ID"));

//        boolean exists = requestsCardRepo.hasPendingRequest(
//                customer, cardType, cardVariant, StatusOfRequest.PENDING_REVIEW
//        );
//
//        if (exists) {
//            throw new RuntimeException("A request for this card is already pending!");
//        }

        RequestNewCard request = new RequestNewCard();
        request.setCardType(cardType);
        request.setCardVariant(cardVariant);
        request.setReason(reason);
        request.setStatusOfRequest(StatusOfRequest.PENDING_REVIEW);
        request.setLocalDate(LocalDate.now());
        request.setCustomer(customer);

        RequestNewCard saved = requestsCardRepo.save(request);

        return new ResponseDto(saved);
    }

    @Override
    public List<ResponseDto> getAllRequests() {
        List<RequestNewCard> requests = requestsCardRepo.findAll();
        return requests.stream().map(ResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ResponseDto getRequestById(Long requestId) {
        RequestNewCard request = requestsCardRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        return new ResponseDto(request);
    }

    @Override
    public ResponseDto updateRequest(Long requestId, RequestsDto requestsDto) {

        RequestNewCard request = requestsCardRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        request.setStatusOfRequest(StatusOfRequest.APPROVED,Sta);

        Customers saveUpdatedCustomer = customersRepo.save(customer);
        return new CustomersResponseDto(saveUpdatedCustomer);
    }
}
