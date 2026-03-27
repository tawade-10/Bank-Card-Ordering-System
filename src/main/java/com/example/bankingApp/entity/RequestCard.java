package com.example.bankingApp.entity;

import jakarta.persistence.*;

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
}
