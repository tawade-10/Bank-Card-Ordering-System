package com.example.bankingApp.entity.CardDetails;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.RequestNewCard.CardType;
import com.example.bankingApp.entity.RequestNewCard.CardVariant;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "card_details")
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true)
    private Long cardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @Column(name = "card_number", unique = true, nullable = false)
    private Long cardNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_type_id", nullable = false)
    private CardType cardType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_variant_id", nullable = false)
    private CardVariant cardVariant;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}