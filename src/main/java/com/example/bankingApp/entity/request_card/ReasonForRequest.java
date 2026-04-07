package com.example.bankingApp.entity.request_card;

import jakarta.persistence.*;

@Entity
@Table(name = "reason")
public class ReasonForRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id", unique = true)
    private Long reasonId;

    @Column(name = "reason_name", unique = true)
    private String reasonName;

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
}
