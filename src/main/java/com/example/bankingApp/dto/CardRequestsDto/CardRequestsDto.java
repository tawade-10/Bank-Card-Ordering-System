package com.example.bankingApp.dto.CardRequestsDto;

import com.example.bankingApp.entity.RequestCard;
import jakarta.validation.constraints.NotBlank;

public class CardRequestsDto {

    @NotBlank(message = "Card Type should be selected")
    private String cardType;

    @NotBlank(message = "Card Variant should be selected")
    private String cardVariant;

    @NotBlank(message = "Reason should be selected")
    private String reason;

    public CardRequestsDto(RequestCard requestCard) {
        this.cardType = requestCard.getCardType();
        this.cardVariant = requestCard.getCardVariant();
        this.reason = requestCard.getReason();
    }

    public CardRequestsDto() {
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
