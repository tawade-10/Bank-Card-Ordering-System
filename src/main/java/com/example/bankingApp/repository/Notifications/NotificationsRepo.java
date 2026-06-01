package com.example.bankingApp.repository.Notifications;

import com.example.bankingApp.entity.Notification.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationsRepo extends JpaRepository<Notifications, Long> {

    Optional<Notifications> findByCustomerCustomerIdAndTypeAndReferenceId(
            Long customerId,
            String type,
            Long referenceId
    );

    List<Notifications> findByCustomerCustomerIdOrderByUpdatedAtDesc(Long customerId);

    List<Notifications> findTop5ByCustomerCustomerIdOrderByUpdatedAtDesc(Long customerId);

    List<Notifications> findByCustomerCustomerIdAndTypeOrderByUpdatedAtDesc(Long customerId, String type);

    Optional<Notifications> findTop1ByCustomerCustomerIdOrderByUpdatedAtDesc(Long customerId);

    Optional<Notifications> findByCustomerCustomerIdAndType(Long customerId, String type);
}