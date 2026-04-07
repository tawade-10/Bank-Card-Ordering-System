package com.example.bankingApp.repository.card;

import com.example.bankingApp.entity.request_card.RequestCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestsCardRepo extends JpaRepository<RequestCard,Long> {

    List<RequestCard> findByCustomerEmail(String email);

//    @Query("""
//    SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
//    FROM RequestCard r
//    WHERE r.customer = :customer
//      AND r.cardType = :cardType
//      AND r.cardVariant = :cardVariant
//      AND r.statusOfRequest = :status
//""")
//    boolean hasPendingRequest(Customers customer,
//                              CardType cardType,
//                              CardVariant cardVariant,
//                              StatusOfRequest status);
}
