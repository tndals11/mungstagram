package com.example.mungstragram.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
        SELECT n FROM Notification n
        LEFT JOIN FETCH n.sender
        LEFT JOIN FETCH n.post
        WHERE n.id = :id AND n.receiver.id = :userId
    """)
    Optional<Notification> findByIdNotificationWithUser(@Param("id") Long id, @Param("userId") Long userId);

    @Query("""
        SELECT n FROM Notification n
        JOIN FETCH n.sender
        WHERE n.receiver.id = :userId
        ORDER BY n.createdAt DESC
        """)
    List<Notification> findAllByReceiverId(@Param("userId") Long userId);
}
