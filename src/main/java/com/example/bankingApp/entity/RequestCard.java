package com.example.bankingApp.entity;

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

    @Column(name = "card_type", nullable = false)
    private String cardType;

    @Column(name = "card_variant", nullable = false)
    private String cardVariant;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardVariant() {
        return cardVariant;
    }

    public void setCardVariant(String cardVariant) {
        this.cardVariant = cardVariant;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
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
