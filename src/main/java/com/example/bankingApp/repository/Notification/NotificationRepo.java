package com.example.bankingApp.repository.Notification;

import com.example.bankingApp.entity.Customers.Customers;
import com.example.bankingApp.entity.Notification.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notifications, Long> {

    List<Notifications> findByCustomerOrderByCreatedAtDesc(Customers customer);

    Optional<Notifications> findByCustomerCustomerIdAndTypeAndReferenceId(
            Long customerId,
            String type,
            Long referenceId
    );

    List<Notifications> findByCustomerCustomerIdAndTypeOrderByCreatedAtDesc(
            Long customerId,
            String type
    );

    Notifications findTop1ByCustomerCustomerIdOrderByUpdatedAtDesc(Long customerId);
}