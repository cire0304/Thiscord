package com.example.thiscode.core.commutity.service;

import com.example.thiscode.core.commutity.entity.Room;
import com.example.thiscode.core.commutity.entity.RoomUser;
import com.example.thiscode.core.commutity.repository.RoomRepository;
import com.example.thiscode.core.commutity.repository.RoomUserRepository;
import com.example.thiscode.core.commutity.service.dto.RoomDmInfo;
import com.example.thiscode.core.commutity.controller.request.ShowRoomsResponse;
import com.example.thiscode.core.commutity.entity.type.RoomType;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;

    // TODO: consider replacing UserRepository with another interface repository for reduce dependency
    private final UserRepository userRepository;

    // TODO: return type is good to be DTO not Entity.
    @Transactional
    public Room createDmRoom(Long senderId, Long receiverId) {
        if (Objects.equals(senderId, receiverId)) {
            throw new IllegalArgumentException("자기 자신과의 방을 만들 수 없습니다.");
        }

        List<Long> roomIdsOfSender = roomUserRepository.findByUserId(senderId).stream()
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        Set<Long> roomsIdsOfReceiver = roomUserRepository.findByUserId(receiverId).stream()
                .map(roomUser -> roomUser.getRoom().getId())
                .collect(Collectors.toSet());

        for (Long roomIdOfSender : roomIdsOfSender) {
            if (roomsIdsOfReceiver.contains(roomIdOfSender)) {
                throw new EntityExistsException("이미 상대방과의 방이 존재합니다.");
            }
        }

        Room room = Room.createDmRoom();
        roomRepository.save(room);
        RoomUser roomUser = new RoomUser(room, senderId);
        RoomUser roomUser2 = new RoomUser(room, receiverId);
        roomUserRepository.save(roomUser);
        roomUserRepository.save(roomUser2);

        return room;
    }

    // TODO: consider processing group room later
    // TODO: consider performance later
    @Transactional
    public List<RoomDmInfo> getRoomList(Long userId) {
        List<Long> roomIds = roomUserRepository.findByUserId(userId).stream()
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        // TODO: check if below findAllById use where in query
        List<Room> rooms = roomRepository.findAllById(roomIds);
        List<RoomDmInfo> roomDmInfos = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType() != RoomType.DM) continue;

            // TODO: refactor this code as using batch query
            RoomUser roomUser1 = room.getRoomUsers().stream()
                    .filter(roomUser -> !Objects.equals(roomUser.getUserId(), userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));
            User user = userRepository.findById(roomUser1.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
            roomDmInfos.add(new RoomDmInfo(room.getId(), user.getNickname()));
        }

        return roomDmInfos;
    }

    @Transactional
    public void exitDmRoom(Long userId, Long roomId) {
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));
        roomUserRepository.delete(roomUser);

        Room room = roomUser.getRoom();
        room.onUserExit();
        if (room.isEmptyMember()) {
            roomRepository.delete(room);
        }
        // TODO: if you develop state server later, you should send message to other user that this user exit room here
    }
}
