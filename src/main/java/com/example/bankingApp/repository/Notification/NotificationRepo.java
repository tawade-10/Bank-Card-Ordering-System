package com.example.bankingApp.repository.Notification;

import com.example.bankingApp.entity.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByUpdatedAtDesc(Long userId);

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.updatedAt DESC")
    Notification findTopByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<Notification> findByUserIdAndTypeAndReferenceId(Long userId, String type, Long referenceId);
}