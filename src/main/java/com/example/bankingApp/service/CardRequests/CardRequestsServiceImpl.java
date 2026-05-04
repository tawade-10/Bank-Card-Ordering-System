package com.example.bankingApp.service.CardRequests;

import com.example.bankingApp.dto.CardRequestsDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.ResponseDto;
import com.example.bankingApp.entity.CardRequests.*;
import com.example.bankingApp.entity.CardRequests.NetworkBin;
import com.example.bankingApp.entity.Enums.Status;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.CardRequests.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notification.NotificationService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardRequestsServiceImpl implements CardRequestsService {

    private final CardRequestsRepo cardRequestsRepo;

    private final CustomersRepo customersRepo;

    private final CardTypeRepo cardTypeRepo;

    private final CardVariantRepo cardVariantRepo;

    private final CardNetworkRepo cardNetworkRepo;

    private final ReasonForRequestRepo reasonForRequestRepo;

    private final NetworkBinRepo networkBinRepo;

    private final NotificationService notificationService;

    public CardRequestsServiceImpl(CardRequestsRepo cardRequestsRepo, CustomersRepo customersRepo, CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, CardNetworkRepo cardNetworkRepo, ReasonForRequestRepo reasonForRequestRepo, NetworkBinRepo networkBinRepo, NotificationService notificationService) {
        this.cardRequestsRepo = cardRequestsRepo;
        this.customersRepo = customersRepo;
        this.cardTypeRepo = cardTypeRepo;
        this.cardVariantRepo = cardVariantRepo;
        this.cardNetworkRepo = cardNetworkRepo;
        this.reasonForRequestRepo = reasonForRequestRepo;
        this.networkBinRepo = networkBinRepo;
        this.notificationService = notificationService;
    }

    @Override
    public ResponseDto createRequest(RequestsDto requestsDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        CardType cardType = cardTypeRepo.findById(requestsDto.getCardTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid Card Type ID"));

        CardVariant cardVariant = cardVariantRepo.findById(requestsDto.getCardVariantId())
                .orElseThrow(() -> new RuntimeException("Invalid Card Variant ID"));

        CardNetwork cardNetwork = cardNetworkRepo.findById(requestsDto.getCardNetworkId())
                .orElseThrow(() -> new RuntimeException("Invalid Network ID"));

        NetworkBin networkBin = networkBinRepo.findByCardNetwork(cardNetwork);

        Reason reason = reasonForRequestRepo.findById(requestsDto.getReasonId())
                .orElseThrow(() -> new RuntimeException("Invalid Reason ID"));

        CardRequests request = new CardRequests();
        request.setCardType(cardType);
        request.setCardVariant(cardVariant);
        request.setCardNetwork(cardNetwork);
        request.setReason(reason);
        request.setNetworkBin(networkBin);
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

    @Override
    public ResponseDto getBinByNetwork(Long networkId) {

        CardNetwork network = cardNetworkRepo.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Network not found with ID: " + networkId));

        NetworkBin bin = networkBinRepo.findByCardNetwork(network);

        ResponseDto dto = new ResponseDto();
        dto.setCardNetworkId(network.getNetworkId());
        dto.setCardNetwork(network.getNetworkName());
        dto.setBin(bin.getBinNumber());

        return dto;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseDto reviewRequest(Long requestId, RequestsDto requestsDto) {

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        if (request.getStatus() != Status.PENDING_REVIEW) {
            throw new RuntimeException("Only PENDING_REVIEW requests can be approved or rejected.");
        }

        if (requestsDto.getStatus() != Status.APPROVED && requestsDto.getStatus() != Status.REJECTED) {
            throw new RuntimeException("Invalid status. Only APPROVED or REJECTED allowed here.");
        }

        if (requestsDto.getReviewMessage() == null || requestsDto.getReviewMessage().trim().isEmpty()) {
            throw new RuntimeException("Reason cannot be empty");
        }

        request.setStatus(requestsDto.getStatus());
        request.setReviewMessage(requestsDto.getReviewMessage());
        CardRequests saved = cardRequestsRepo.save(request);

//        notificationService.notify(
//                request.getCustomers().getCustomerId(),
//                "Card Request Update",
//                "Your card request has been " + request.getStatus() +
//                        ". Reason: " + request.getReviewMessage()
//        );

        return new ResponseDto(saved);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseDto updateRequestStatus(Long requestId, RequestsDto requestsDto) {

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        Status current = request.getStatus();
        Status next = requestsDto.getStatus();

        if (current == Status.REJECTED) {
            throw new RuntimeException("Request already rejected.");
        }

        if (current == Status.PENDING_REVIEW) {
            throw new RuntimeException("Please review (approve/reject) the request first.");
        }

        if (current == Status.APPROVED && next != Status.PRINTING) {
            throw new RuntimeException("APPROVED → PRINTING is the only valid transition.");
        }

        if (current == Status.PRINTING && next != Status.DISPATCHED) {
            throw new RuntimeException("PRINTING → DISPATCHED only.");
        }

        if (current == Status.DISPATCHED && next != Status.DELIVERED) {
            throw new RuntimeException("DISPATCHED → DELIVERED only.");
        }

        if (current == Status.DELIVERED) {
            throw new RuntimeException("Request already delivered.");
        }

        request.setStatus(next);
        return new ResponseDto(cardRequestsRepo.save(request));
    }
}
