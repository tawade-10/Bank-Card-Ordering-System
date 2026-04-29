package com.example.bankingApp.repository.CardRequests;

import com.example.bankingApp.entity.CardRequests.CardNetwork;
import com.example.bankingApp.entity.CardRequests.NetworkBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkBinRepo extends JpaRepository<NetworkBin,Long> {
    NetworkBin findByCardNetwork(CardNetwork network);
}
