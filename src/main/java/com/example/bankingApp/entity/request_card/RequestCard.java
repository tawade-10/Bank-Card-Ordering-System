package com.example.bankingApp.entity.request_card;

import com.example.bankingApp.entity.customer.Customers;
import com.example.bankingApp.entity.enums.StatusOfRequest;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "request_card")
public class RequestCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "card_type_id", nullable = false)
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "card_variant_id", nullable = false)
    private CardVariant cardVariant;

    @ManyToOne
    @JoinColumn(name = "reason_id", nullable = false)
    private ReasonForRequest reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOfRequest statusOfRequest;

    @Column(name = "date")
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardVariant getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(CardVariant cardVariant) {
        this.cardVariant = cardVariant;
    }

    public ReasonForRequest getReason() {
        return reason;
    }

    public void setReason(ReasonForRequest reason) {
        this.reason = reason;
    }

    public StatusOfRequest getStatusOfRequest() {
        return statusOfRequest;
    }

    public void setStatusOfRequest(StatusOfRequest statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
}