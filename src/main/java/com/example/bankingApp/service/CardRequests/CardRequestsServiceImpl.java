package com.example.bankingApp.service.CardRequests;

import com.example.bankingApp.dto.CardRequestsDto.CreationDto.RequestsDto;
import com.example.bankingApp.dto.CardRequestsDto.CreationDto.ResponseDto;
import com.example.bankingApp.dto.NetworkDto.NetworkResponseDto;
import com.example.bankingApp.dto.Notifications.NotificationsRequestDto;
import com.example.bankingApp.dto.ReviewDto.ReviewRequestsDto;
import com.example.bankingApp.dto.ReviewDto.ReviewResponseDto;
import com.example.bankingApp.entity.CardRequests.*;
import com.example.bankingApp.entity.CardRequests.NetworkBin;
import com.example.bankingApp.entity.Enums.RequestStatus;
import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.repository.CardRequests.*;
import com.example.bankingApp.repository.Customers.CustomersRepo;
import com.example.bankingApp.service.Notifications.NotificationsService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    private final NotificationsService notificationsService;

    public CardRequestsServiceImpl(CardRequestsRepo cardRequestsRepo, CustomersRepo customersRepo, CardTypeRepo cardTypeRepo, CardVariantRepo cardVariantRepo, CardNetworkRepo cardNetworkRepo, ReasonForRequestRepo reasonForRequestRepo, NetworkBinRepo networkBinRepo, NotificationsService notificationsService) {
        this.cardRequestsRepo = cardRequestsRepo;
        this.customersRepo = customersRepo;
        this.cardTypeRepo = cardTypeRepo;
        this.cardVariantRepo = cardVariantRepo;
        this.cardNetworkRepo = cardNetworkRepo;
        this.reasonForRequestRepo = reasonForRequestRepo;
        this.networkBinRepo = networkBinRepo;
        this.notificationsService = notificationsService;
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

//        Optional<CardRequests> existingPending =
//                cardRequestsRepo.findPendingRequest(customer, cardType);
//
//        if (existingPending.isPresent()) {
//            throw new RuntimeException("A request for this card is already pending review.");
//        }

        CardRequests request = new CardRequests();
        request.setCardType(cardType);
        request.setCardVariant(cardVariant);
        request.setCardNetwork(cardNetwork);
        request.setReason(reason);
        request.setNetworkBin(networkBin);
        request.setRequestStatus(RequestStatus.PENDING_REVIEW);
        request.setCreatedDate(LocalDate.now());
        request.setCreatedTime(LocalTime.now());
        request.setCustomers(customer);

        CardRequests saved = cardRequestsRepo.save(request);

        String notificationMessage =
                "Your Request for " + cardType.getTypeName() + " CARD has been created.";

        NotificationsRequestDto cardDto = new NotificationsRequestDto();
        cardDto.setCustomerId(customer.getCustomerId());
        cardDto.setTitle("Card Request Created Successfully");
        cardDto.setMessage(notificationMessage);
        cardDto.setType("CARD_REQUEST");
        cardDto.setReferenceId(saved.getRequestId());

        notificationsService.createNotifications(cardDto);

        ResponseDto response = new ResponseDto(saved);
        response.setMessage(notificationMessage);

        return response;
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
    public List<ResponseDto> getRequestsByEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        Customers customer = customersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        List<CardRequests> listOfRequests = cardRequestsRepo.findByCustomersEmail(email);

        return listOfRequests.stream().map(ResponseDto::new).toList();
    }

    @Override
    public NetworkResponseDto getBinByNetwork(Long networkId) {

        CardNetwork network = cardNetworkRepo.findById(networkId)
                .orElseThrow(() -> new RuntimeException("Network not found with ID: " + networkId));

        NetworkBin bin = networkBinRepo.findByCardNetwork(network);

        NetworkResponseDto dto = new NetworkResponseDto();
        dto.setBin(bin.getBinNumber());

        return dto;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ReviewResponseDto reviewRequest(Long requestId, ReviewRequestsDto reviewRequestsDto) {

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        if (request.getRequestStatus() != RequestStatus.PENDING_REVIEW) {
            throw new RuntimeException("Only PENDING_REVIEW requests can be approved or rejected.");
        }

        if (reviewRequestsDto.getRequestStatus() != RequestStatus.APPROVED &&
                reviewRequestsDto.getRequestStatus() != RequestStatus.REJECTED) {
            throw new RuntimeException("Invalid status. Only APPROVED or REJECTED allowed here.");
        }

        if (reviewRequestsDto.getReviewMessage() == null ||
                reviewRequestsDto.getReviewMessage().trim().isEmpty()) {
            throw new RuntimeException("Reason cannot be empty");
        }

        request.setRequestStatus(reviewRequestsDto.getRequestStatus());
        request.setReviewMessage(reviewRequestsDto.getReviewMessage());
        request.setUpdatedDate(LocalDate.now());
        request.setUpdatedTime(LocalTime.now());

        CardRequests saved = cardRequestsRepo.save(request);

        NotificationsRequestDto cardDto = new NotificationsRequestDto();
        cardDto.setCustomerId(request.getCustomers().getCustomerId());
        cardDto.setTitle("Card Request Reviewed");
        cardDto.setMessage("Your Request for " + request.getCardType().getTypeName() +
                        " card has been " + reviewRequestsDto.getRequestStatus());
        cardDto.setType("CARD_REQUEST");
        cardDto.setReferenceId(saved.getRequestId());

        notificationsService.createNotifications(cardDto);

        return new ReviewResponseDto(saved);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ReviewResponseDto updateRequestStatus(Long requestId, ReviewRequestsDto reviewRequestsDto) {

        CardRequests request = cardRequestsRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        RequestStatus current = request.getRequestStatus();
        RequestStatus next = reviewRequestsDto.getRequestStatus();

        if (current == RequestStatus.REJECTED) {
            throw new RuntimeException("Request already rejected.");
        }

        if (current == RequestStatus.PENDING_REVIEW) {
            throw new RuntimeException("Please review (approve/reject) the request first.");
        }

        if (current == RequestStatus.APPROVED && next != RequestStatus.PRINTED) {
            throw new RuntimeException("APPROVED → PRINTED is the only valid transition.");
        }

        if (current == RequestStatus.PRINTED && next != RequestStatus.DISPATCHED) {
            throw new RuntimeException("PRINTING → DISPATCHED only.");
        }

        if (current == RequestStatus.DISPATCHED && next != RequestStatus.DELIVERED) {
            throw new RuntimeException("DISPATCHED → DELIVERED only.");
        }

        if (current == RequestStatus.DELIVERED) {
            throw new RuntimeException("Request already delivered.");
        }

        request.setRequestStatus(next);
        request.setUpdatedDate(LocalDate.now());
        request.setUpdatedTime(LocalTime.now());
        return new ReviewResponseDto(cardRequestsRepo.save(request));
    }
}
