package com.example.bankingApp.entity.request_card;

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
}
