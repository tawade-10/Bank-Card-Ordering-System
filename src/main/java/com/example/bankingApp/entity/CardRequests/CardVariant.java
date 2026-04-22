package com.example.bankingApp.entity.CardRequests;

import jakarta.persistence.*;

@Entity
@Table(name = "card_variant")
public class CardVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id", unique = true)
    private Long variantId;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "card_colour_front")
    private String cardColourFront;

    @Column(name = "card_colour_back")
    private String cardColourBack;

    @Column(name = "chip_colour")
    private String chipColour;

    @Column(name = "text_colour")
    private String textColour;

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getCardColourFront() {
        return cardColourFront;
    }

    public void setCardColourFront(String cardColourFront) {
        this.cardColourFront = cardColourFront;
    }

    public String getCardColourBack() {
        return cardColourBack;
    }

    public void setCardColourBack(String cardColourBack) {
        this.cardColourBack = cardColourBack;
    }

    public String getChipColour() {
        return chipColour;
    }

    public void setChipColour(String chipColour) {
        this.chipColour = chipColour;
    }

    public String getTextColour() {
        return textColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
    }
}
