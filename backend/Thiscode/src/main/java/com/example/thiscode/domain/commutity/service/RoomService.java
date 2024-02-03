package com.example.thiscode.domain.commutity.service;

import com.example.thiscode.domain.commutity.dto.GroupRoomDTO;
import com.example.thiscode.domain.commutity.dto.RoomListDTO;
import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.entity.type.RoomType;
import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
import com.example.thiscode.domain.commutity.event.CommunityEventPublisher;
import com.example.thiscode.domain.commutity.event.RoomEventType;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final CommunityEventPublisher communityEventPublisher;

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

            RoomUserDTO roomUserDTO = getRoomUserDTO(receiveUser);
            return new DmRoomDTO(
                    roomUser.getRoom().getId(),
                    roomUserDTO);
        }

        Room room = createRoom(senderId, receiverId);
        RoomUserDTO roomUserDTO = getRoomUserDTO(receiveUser);
        return new DmRoomDTO(
                room.getId(),
                roomUserDTO);
    }

    private static RoomUserDTO getRoomUserDTO(User receiveUser) {
        return RoomUserDTO.builder()
                .userId(receiveUser.getId())
                .email(receiveUser.getEmail())
                .nickname(receiveUser.getNickname())
                .userCode(receiveUser.getUserCode())
                .introduction(receiveUser.getIntroduction())
                .state(RoomUserState.JOIN)
                .build();
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

    public GroupRoomDTO createGroupRoom(Long requestUserId, String groupName, List<Long> otherUserIds) {
        if (otherUserIds.isEmpty() || otherUserIds.size() > 9) {
            throw new IllegalArgumentException("상대방의 아이디를 1이상 9개 이하로 입력해주세요.");
        }
        ArrayList<Long> userIds = new ArrayList<>(otherUserIds);
        userIds.add(requestUserId);

        List<RoomUserDTO> roomUserDTOs = userRepository.findAllById(userIds)
                .stream()
                .map(RoomService::getRoomUserDTO)
                .toList();

        if(roomUserDTOs.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 사용자가 있습니다.");
        }

        Room room = Room.createGroupRoom(groupName, userIds.size());
        userIds.stream()
                .map(userId -> new RoomUser(room, userId))
                .forEach(room::addRoomUser);
        Room saveRoom = roomRepository.save(room);
        return new GroupRoomDTO(saveRoom.getId(), saveRoom.getName(), roomUserDTOs);
    }

    @Transactional
    public RoomListDTO getRoomListByUserId(Long requestUserId) {
        List<Long> roomIdsJoinedRequestUser = roomUserRepository.findAllByUserId(requestUserId).stream()
                .filter(RoomUser::isJoin)
                .map(roomUser -> roomUser.getRoom().getId())
                .toList();

        List<Room> dmRooms = new ArrayList<>();
        List<Room> groupRooms = new ArrayList<>();
        roomRepository.findAllById(roomIdsJoinedRequestUser)
                .forEach(room -> {
                    if (room.getType() == RoomType.DM) {
                        dmRooms.add(room);
                    } else {
                        groupRooms.add(room);
                    }
                });
        List<DmRoomDTO> dmRoomDTOs = getDmRoomDTOs(requestUserId, dmRooms);
        List<GroupRoomDTO> groupRoomDTOs = getGroupRoomDTOs(groupRooms);

        return new RoomListDTO(dmRoomDTOs, groupRoomDTOs);
    }

    private List<DmRoomDTO> getDmRoomDTOs(Long requestUserId, List<Room> dmRooms) {
        List <Long> roomIds = dmRooms.stream()
                .map(Room::getId)
                .toList();
        // <UserId, RoomUser>
        Map<Long, RoomUser> roomUserMap = roomUserRepository.findAllByRoomIdIn(roomIds).stream()
                .filter(roomUser -> !Objects.equals(roomUser.getUserId(), requestUserId))
                .collect(Collectors.toMap(RoomUser::getUserId, roomUser -> roomUser));

        return userRepository.findAllById(roomUserMap.keySet())
                .stream()
                .map(user -> {
                    RoomUser roomUser = roomUserMap.get(user.getId());
                    return new DmRoomDTO(roomUser.getRoom().getId(), getRoomUserDTO(user));
                }).toList();
    }

    private List<GroupRoomDTO> getGroupRoomDTOs(List<Room> groupRooms) {
        List <Long> roomIds = groupRooms.stream()
                .map(Room::getId)
                .toList();

        // <RoomId, List<RoomUser>>
        Map<Long, List<RoomUser>> roomUserListMap = getRoomUserListMap(roomIds);

        // <UserId, User>
        Map<Long, User> usersMap = getUserMap(roomUserListMap);

        List<GroupRoomDTO> groupRoomDTOs = new ArrayList<>();
        for (Room room : groupRooms) {
            Long roomId = room.getId();
            String roomName = room.getName();

            List<RoomUser> roomUsers = roomUserListMap.get(roomId);
            List<RoomUserDTO> roomUserDTOS = roomUsers.stream()
                    .map(roomUser -> usersMap.get(roomUser.getUserId()))
                    .map(RoomService::getRoomUserDTO)
                    .toList();

            groupRoomDTOs.add(new GroupRoomDTO(roomId, roomName, roomUserDTOS));
        }
        return groupRoomDTOs;
    }

    private Map<Long, User> getUserMap(Map<Long, List<RoomUser>> roomUserListMap) {
        List<Long> userIds = roomUserListMap.values().stream()
                .flatMap(Collection::stream)
                .map(RoomUser::getUserId)
                .toList();

        return userRepository.findAllById(userIds)
                .stream()
                .map(user -> Map.entry(user.getId(), user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Long, List<RoomUser>> getRoomUserListMap(List<Long> roomIds) {
        return roomUserRepository.findAllByRoomIdIn(roomIds).stream()
                .filter(RoomUser::isJoin)
                .collect(Collectors.groupingBy(roomUser -> roomUser.getRoom().getId()));
    }

    @Transactional
    public void exitRoom(Long userId, Long roomId) {
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("방에 참여한 사용자가 아닙니다."));
        roomUser.exit();

        Room room = roomUser.getRoom();
        room.onUserExit();
        if (room.isEmptyMember()) {
            roomRepository.delete(room);
            roomUserRepository.deleteAll(roomUserRepository.findAllByRoomId(roomId));
        }
        communityEventPublisher.publish(roomUser, RoomEventType.EXIT_ROOM);
    }

    @Transactional
    public RoomUserDTO findRoomUser(Long userId, Long roomId) {
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new EntityNotFoundException("방에 참여한 사용자가 아닙니다."));

        User user = userRepository.findById(roomUser.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        return RoomUserDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .userCode(user.getUserCode())
                .email(user.getEmail())
                .introduction(user.getIntroduction())
                .state(roomUser.getState())
                .build();
    }

    @Transactional
    public void inviteUserToRoom(Long senderId, Long receiverId, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 방입니다."));

        room.getRoomUsers().stream().filter(roomUser -> Objects.equals(roomUser.getUserId(), senderId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("초대를 사용자는 방에 참여한 사용자가 아닙니다."));

        room.getRoomUsers().stream()
                .filter(roomUser -> roomUser.getUserId().equals(receiverId))
                .findAny()
                .ifPresentOrElse(
                        roomUser -> {
                            if (roomUser.isJoin()) {
                                throw new IllegalArgumentException("이미 방에 참여한 사용자입니다.");
                            }
                            roomUser.join();
                            communityEventPublisher.publish(roomUser, RoomEventType.INVITE_ROOM);
                        },
                        () -> {
                            RoomUser roomUser = RoomUser.invitedRoomUser(room, receiverId);
                            room.addRoomUser(roomUser);
                            communityEventPublisher.publish(roomUser, RoomEventType.INVITE_ROOM);
                        }
                );
    }

}
