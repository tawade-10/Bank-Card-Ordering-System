package com.example.bankingApp.entity.CardRequests;

import jakarta.persistence.*;

@Entity
@Table(name = "card_network")
public class CardNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "network_id", unique = true)
    private Long networkId;

    @Column(name = "network_name")
    private String networkName;

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }
}
