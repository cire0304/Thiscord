package com.example.thiscode.domain.commutity.entity;

import com.example.thiscode.domain.common.BaseEntity;
import com.example.thiscode.domain.commutity.entity.type.RoomType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@Entity
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomUser> roomUsers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoomType type;

    @Column(name = "user_count")
    private int userCount;


    public static Room createDmRoom() {
        int initialUserCount = 2;
        return new Room("DM", RoomType.DM, initialUserCount);
    }

    public static Room createGroupRoom() {
        int initialUserCount = 1;
        return new Room("GROUP", RoomType.GROUP, initialUserCount);
    }

    private Room(String name, RoomType type, int userCount) {
        this.name = name;
        this.type = type;
        this.userCount = userCount;
    }

    public void addRoomUser(RoomUser roomUser) {
        this.roomUsers.add(roomUser);
    }

    public void onUserExit() {
        userCount--;
    }

    public boolean isEmptyMember() {
        return userCount == 0;
    }

}
