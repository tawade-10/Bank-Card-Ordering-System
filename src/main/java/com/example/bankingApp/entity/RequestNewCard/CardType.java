package com.example.bankingApp.entity.RequestNewCard;

import jakarta.persistence.*;

@Entity
@Table(name = "card_type")
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", unique = true)
    private Long typeId;

    @Column(name = "type_name")
    private String typeName;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
