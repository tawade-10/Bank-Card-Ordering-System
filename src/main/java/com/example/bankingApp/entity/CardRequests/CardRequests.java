package com.example.bankingApp.entity.CardRequests;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "request_card")
public class CardRequests {

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
    private Reason reason;

    @ManyToOne
    @JoinColumn(name = "network_id", nullable = false)
    private CardNetwork cardNetwork;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    private NetworkBin networkBin;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus;

    @Column(name = "date")
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @Column(name = "message")
    private String reviewMessage;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_time")
    private LocalTime createdTime;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "updated_time")
    private LocalTime updatedTime;

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

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public CardNetwork getCardNetwork() { return cardNetwork; }

    public void setCardNetwork(CardNetwork cardNetwork) { this.cardNetwork = cardNetwork; }

    public NetworkBin getNetworkBin() {
        return networkBin;
    }

    public void setNetworkBin(NetworkBin networkBin) {
        this.networkBin = networkBin;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public LocalDate getCreatedDate() { return createdDate; }

    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public LocalTime getCreatedTime() { return createdTime; }

    public void setCreatedTime(LocalTime createdTime) { this.createdTime = createdTime; }

    public LocalDate getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(LocalDate updatedDate) { this.updatedDate = updatedDate; }

    public LocalTime getUpdatedTime() { return updatedTime; }

    public void setUpdatedTime(LocalTime updatedTime) { this.updatedTime = updatedTime; }
}