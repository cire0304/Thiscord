package com.example.thiscode.domain.commutity.service;

import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.commutity.service.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.entity.type.RoomType;
import com.example.thiscode.domain.commutity.service.dto.RoomUserDTO;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.repository.UserRepository;
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

        List<Long> roomIdsOfSender = roomUserRepository.findAllByUserId(senderId).stream()
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        Set<Long> roomsIdsOfReceiver = roomUserRepository.findAllByUserId(receiverId).stream()
                .map(roomUser -> roomUser.getRoom().getId())
                .collect(Collectors.toSet());

        for (Long roomIdOfSender : roomIdsOfSender) {
            if (roomsIdsOfReceiver.contains(roomIdOfSender)) {
                // TODO: in case of user exited and re-entered room, this code cause bug
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
    public List<DmRoomDTO> getRoomList(Long userId) {
        // TODO: check if below findAllByUserId make N+1 problem
        List<Long> roomIds = roomUserRepository.findAllByUserId(userId).stream()
                .filter(RoomUser::isJoin)
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        // TODO: check if below findAllById use where in query
        List<Room> rooms = roomRepository.findAllById(roomIds);
        List<DmRoomDTO> roomDmDTOs = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType() != RoomType.DM) continue;

            // TODO: refactor this code as using batch query
            RoomUser roomUser1 = room.getRoomUsers().stream()
                    .filter(roomUser -> !Objects.equals(roomUser.getUserId(), userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));
            User user = userRepository.findById(roomUser1.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
            roomDmDTOs.add(new DmRoomDTO(room.getId(), user.getId(), user.getNickname()));
        }

        return roomDmDTOs;
    }

    @Transactional
    public void exitDmRoom(Long userId, Long roomId) {
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));
        roomUser.exit();

        Room room = roomUser.getRoom();
        room.onUserExit();
        if (room.isEmptyMember()) {
            roomRepository.delete(room);
            roomUserRepository.deleteAll(roomUserRepository.findAllByRoomId(roomId));
        }
        // TODO: if you develop state server later, you should send message to other user that this user exit room here
    }

    @Transactional
    public RoomUserDTO findRoomUser(Long userIdRequestBy, Long roomId, Long userId) {
        roomUserRepository.findByRoomIdAndUserId(roomId, userIdRequestBy)
                .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));

        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));

        User user = userRepository.findById(roomUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return RoomUserDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .userCode(user.getUserCode())
                .email(user.getEmail())
                .introduction(user.getIntroduction())
                .state(roomUser.getState())
                .build();
    }
}
