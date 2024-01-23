package com.example.thiscode.domain.commutity.service;

import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.event.CommunityEventPublisher;
import com.example.thiscode.domain.commutity.event.RoomEventType;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
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
    private final UserRepository userRepository;
    private final CommunityEventPublisher communityventPublisher;

    @Transactional
    public DmRoomDTO createDmRoom(Long senderId, Long receiverId) {
        if (Objects.equals(senderId, receiverId)) {
            throw new IllegalArgumentException("자기 자신과의 방을 만들 수 없습니다.");
        }
        User receiveUser = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<RoomUser> roomsOfSender = roomUserRepository.findAllByUserId(senderId);
        List<RoomUser> roomsOfReceiver = roomUserRepository.findAllByUserId(receiverId);

        Optional<RoomUser> roomUserOptional = getSenderRoomUserInRoomWithReceiver(roomsOfSender, roomsOfReceiver);
        if (roomUserOptional.isPresent()) {
            RoomUser roomUser = roomUserOptional.get();
            roomUser.join();
            return new DmRoomDTO(
                    roomUser.getRoom().getId(),
                    receiveUser.getId(),
                    receiveUser.getNickname());
        }

        Room room = createRoom(senderId, receiverId);
        return new DmRoomDTO(
                room.getId(),
                receiveUser.getId(),
                receiveUser.getNickname());
    }

    private Room createRoom(Long senderId, Long receiverId) {
        Room room = Room.createDmRoom();
        RoomUser senderRoomUser = new RoomUser(room, senderId);
        RoomUser receiverRoomUser = new RoomUser(room, receiverId);
        room.addRoomUser(senderRoomUser);
        room.addRoomUser(receiverRoomUser);
        roomRepository.save(room);
        return room;
    }

    /**
     * return RoomUser of sender Entity if exist room between sender and receiver.
     * if not exist, return Optional.empty()
     */
    private Optional<RoomUser> getSenderRoomUserInRoomWithReceiver(List<RoomUser> roomUsersOfSender, List<RoomUser> roomUsersOfReceiver) {
        Map<Long, RoomUser> roomUserMapOfSender = roomUsersOfSender.stream()
                .map(roomUser -> Map.entry(roomUser.getRoom().getId(), roomUser))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (RoomUser RoomUserOfReceiver : roomUsersOfReceiver) {
            Long roomId = RoomUserOfReceiver.getRoom().getId();
            if (roomUserMapOfSender.containsKey(roomId))
                return Optional.of(roomUserMapOfSender.get(roomId));
        }
        return Optional.empty();
    }

    // TODO: consider processing group room later
    @Transactional
    public List<DmRoomDTO> getRoomList(Long userId) {
        List<Long> roomIdsJoinedRequestUser = roomUserRepository.findAllByUserId(userId).stream()
                .filter(RoomUser::isJoin)
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        // <RoomId, List<RoomUser>>
        Map<Long, List<RoomUser>> collect = roomUserRepository.findAllByRoomIdIn(roomIdsJoinedRequestUser).stream()
                .filter(roomUser -> !Objects.equals(roomUser.getUserId(), userId))
                .collect(Collectors.groupingBy(roomUser -> roomUser.getRoom().getId()));

        List<Long> userIds = collect.values().stream()
                .flatMap(Collection::stream)
                .map(RoomUser::getUserId)
                .toList();

        // <UserId, User>
        Map<Long, User> usersMap = userRepository.findAllById(userIds)
                .stream()
                .map(user -> Map.entry(user.getId(), user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return collect.entrySet().stream()
                // <RoomId, List<RoomUser>> => <RoomId, List<User>>
                .map(entry -> {
                    Long roomId = entry.getKey();
                    List<User> users = entry.getValue().stream()
                            .map(roomUser -> usersMap.get(roomUser.getUserId()))
                            .toList();
                    return Map.entry(roomId, users);
                })
                // <RoomId, List<User>> => List<DmRoomDTO>
                .map(entry -> {
                    Long roomId = entry.getKey();
                    return entry.getValue().stream()
                            .map(user -> new DmRoomDTO(roomId, user.getId(), user.getNickname()))
                            .toList();
                })
                .flatMap(Collection::stream)
                .toList();
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
        communityventPublisher.publish(roomUser, RoomEventType.EXIT_ROOM);
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
