package com.example.thiscode.domain.commutity.entity;

import com.example.thiscode.domain.common.BaseEntity;
import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
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

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private RoomUserState state;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    public RoomUser(Room room, Long userId) {
        this.room = room;
        this.userId = userId;
        this.state = RoomUserState.JOIN;
        this.joinedAt = LocalDateTime.now();
        this.lastReadAt = LocalDateTime.now();
    }

    private RoomUser(Room room, Long userId, RoomUserState state, LocalDateTime joinedAt, LocalDateTime lastReadAt) {
        this.room = room;
        this.userId = userId;
        this.state = state;
        this.joinedAt = joinedAt;
        this.lastReadAt = lastReadAt;
    }

    public static RoomUser invitedRoomUser(Room room, Long userId) {
        return new RoomUser(room, userId, RoomUserState.INVITE, LocalDateTime.now(), LocalDateTime.now());
    }

    public void join() {
        this.state = RoomUserState.JOIN;
        room.increaseUserCount();
    }

    public void reject() {
        this.state = RoomUserState.REJECT;
    }

    public boolean isJoin() {
        return this.state == RoomUserState.JOIN;
    }

    public boolean isInivte() {
        return this.state == RoomUserState.INVITE;
    }

    public void exit() {
        this.state = RoomUserState.EXIT;
    }

}
