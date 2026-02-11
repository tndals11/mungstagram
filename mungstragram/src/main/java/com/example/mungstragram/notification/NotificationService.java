package com.example.mungstragram.notification;

import com.example.mungstragram._common.enums.notification.Type;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.post.Post;
import com.example.mungstragram.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(User sender, User receiver, Post post, Type type) {

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .post(post)
                .type(type)
                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public NotificationResponse.DetailDTO detailNotification(Long id, Long userId) {

      Notification notificationEntity = notificationRepository.findByIdNotificationWithUser(id, userId).
              orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationEntity.isRead();

        return new NotificationResponse.DetailDTO(notificationEntity);
    }

    public List<NotificationResponse.ListDTO> listNotification(Long userId) {

        System.out.println(userId);

       List<Notification> notificationEntity = notificationRepository.findAllByReceiverId(userId);

        System.out.println(notificationEntity);

       return notificationEntity.stream()
               .map(NotificationResponse.ListDTO::new)
               .toList();
    }
}
