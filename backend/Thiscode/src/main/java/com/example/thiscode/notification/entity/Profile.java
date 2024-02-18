package com.example.thiscode.notification.entity;

import com.example.thiscode.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profile")
@Entity
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "last_access_at")
    private LocalDateTime lastAccessAt;

    public Profile(Long userId, String fcmToken, LocalDateTime lastAccessAt) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.lastAccessAt = lastAccessAt;
    }

    public void updateLastAccessAt(LocalDateTime lastAccessAt) {
        this.lastAccessAt = lastAccessAt;
    }

}
