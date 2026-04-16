package com.example.bankingApp.service.RequestsCard;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.entity.CardRequests.CardType;
import com.example.bankingApp.entity.CardRequests.CardVariant;
import com.example.bankingApp.entity.CardRequests.Reason;
import com.example.bankingApp.entity.CardRequests.CardRequests;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.CardRequests.CardTypeRepo;
import com.example.bankingApp.repository.CardRequests.CardVariantRepo;
import com.example.bankingApp.repository.CardRequests.ReasonForRequestRepo;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.repository.CardRequests.CardRequestsRepo;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestsCardServiceImpl implements RequestsCardService{

    private final CardRequestsRepo cardRequestsRepo;

    private final CustomersRepo customersRepo;

    private final CardTypeRepo cardTypeRepo;

    private final CardVariantRepo cardVariantRepo;

    private final ReasonForRequestRepo reasonForRequestRepo;

    public RequestsCardServiceImpl(CardRequestsRepo cardRequestsRepo, CustomersRepo customersRepo, CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, ReasonForRequestRepo reasonForRequestRepo) {
        this.cardRequestsRepo = cardRequestsRepo;
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

        CardRequests request = new CardRequests();
        request.setCardType(cardType);
        request.setCardVariant(cardVariant);
        request.setReason(reason);
        request.setStatus(Status.PENDING_REVIEW);
        request.setLocalDate(LocalDate.now());
        request.setCustomers(customer);

        CardRequests saved = cardRequestsRepo.save(request);

        return new ResponseDto(saved);
    }

    @Override
    public List<ResponseDto> getAllRequests() {
        List<CardRequests> requests = cardRequestsRepo.findAll(Sort.by(Sort.Direction.ASC, "requestId"));
        return requests.stream().map(ResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ResponseDto getRequestById(Long requestId) {
        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));
        return new ResponseDto(request);
    }

    @Override
    public List<ResponseDto> getRequestsByEmail(Authentication authentication) {

        String email = authentication.getName();

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        List<CardRequests> listOfRequests = cardRequestsRepo.findByCustomersEmail(email);

        return listOfRequests.stream().map(ResponseDto::new).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseDto updateRequest(Long requestId, RequestsDto requestsDto) {

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        if (request.getStatus() != Status.PENDING_REVIEW) {
            throw new RuntimeException("Request already processed! You cannot update it again.");
        }

        request.setStatus(requestsDto.getStatus());

        CardRequests updated = cardRequestsRepo.save(request);

        return new ResponseDto(updated);
    }
}
