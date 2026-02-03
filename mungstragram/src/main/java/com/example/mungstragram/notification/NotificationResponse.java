package com.example.mungstragram.notification;

import com.example.mungstragram._common.enums.notification.Type;
import lombok.Data;

import java.time.LocalDateTime;

public class NotificationResponse {

    @Data
    public static class DetailDTO {
        private String receiverName;
        private String senderName;
        private Type type;
        private LocalDateTime createdAt;
        private boolean isRead;

        public DetailDTO(Notification notification) {
            this.receiverName = notification.getReceiver().getNickname();
            this.senderName = notification.getSender().getNickname();
            this.type = notification.getType();
            this.isRead = notification.getIsRead();
            this.createdAt = notification.getCreatedAt();
        }
    }

    @Data
    public static class ListDTO {
        private Long id;
        private String senderName;
        private Type type;
        private boolean isRead;
        private LocalDateTime createdAt;

        public ListDTO(Notification notification) {
            this.id = notification.getId();
            this.senderName = notification.getSender().getNickname();
            this.type = notification.getType();
            this.isRead = notification.getIsRead();
            this.createdAt = notification.getCreatedAt();
        }
    }

}
