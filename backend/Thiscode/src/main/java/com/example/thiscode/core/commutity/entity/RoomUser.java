package com.example.thiscode.core.commutity.entity;

import com.example.thiscode.core.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_user")
@Entity
public class RoomUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    public RoomUser(Room room, Long userId) {
        this.room = room;
        this.userId = userId;
        this.joinedAt = LocalDateTime.now();
        this.lastReadAt = LocalDateTime.now();
        room.addRoomUser(this);
    }

}
