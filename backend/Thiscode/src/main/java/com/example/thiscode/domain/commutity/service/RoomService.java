package com.example.thiscode.domain.commutity.service;

import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.entity.type.RoomType;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

        List<RoomUser> roomsOfSender = roomUserRepository.findAllByUserId(senderId);
        List<RoomUser> roomsOfReceiver = roomUserRepository.findAllByUserId(receiverId);

        Optional<RoomUser> roomUserOptional = getRoomUserBetweenSenderAndReceiver(roomsOfSender, roomsOfReceiver);
        if (roomUserOptional.isPresent()) {
            RoomUser roomUser = roomUserOptional.get();
            roomUser.join();
            return roomUser.getRoom();
        }

        return createRoom(senderId, receiverId);
    }

    private Room createRoom(Long senderId, Long receiverId) {
        Room room = Room.createDmRoom();
        roomRepository.save(room);
        RoomUser senderRoomUser = new RoomUser(room, senderId);
        RoomUser receiverRoomUser = new RoomUser(room, receiverId);
        roomUserRepository.save(senderRoomUser);
        roomUserRepository.save(receiverRoomUser);
        return room;
    }

    /**
     * return Room Entity if exist room between sender and receiver.
     * if not exist, return Optional.empty()
     */
    private Optional<RoomUser> getRoomUserBetweenSenderAndReceiver(List<RoomUser> roomsOfSender, List<RoomUser> roomsOfReceiver) {
        // TODO: check this code occur N+1 problem
        Map<Long, RoomUser> roomUserMapOfSender = roomsOfSender.stream()
                .map(roomUser -> Map.entry(roomUser.getRoom().getId(), roomUser))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (RoomUser RoomUserOfReceiver : roomsOfReceiver) {
            Long roomId = RoomUserOfReceiver.getRoom().getId();
            if (roomUserMapOfSender.containsKey(roomId))
                return Optional.of(roomUserMapOfSender.get(roomId));
        }
        return Optional.empty();
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
