package com.example.mungstragram.notification;

import com.example.mungstragram._common.base.BaseCreatedEntity;
import com.example.mungstragram._common.enums.notification.Type;
import com.example.mungstragram.post.Post;
import com.example.mungstragram.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification_db")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_notification_receiver_id")
    )
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_notification_sender_id")
    )
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_notification_post_id")
    )
    private Post post;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Type type;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Builder
    public Notification(User receiver, User sender, Post post, Type type) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
        this.type = type;
    }

    public void isRead() {
        this.isRead = true;
    }

}
