package com.example.thiscode.core.commutity.entity.service;

import com.example.thiscode.core.commutity.entity.Room;
import com.example.thiscode.core.commutity.entity.RoomUser;
import com.example.thiscode.core.commutity.repository.RoomRepository;
import com.example.thiscode.core.commutity.repository.RoomUserRepository;
import com.example.thiscode.core.commutity.service.RoomService;
import com.example.thiscode.core.commutity.controller.request.ShowRoomsResponse;
import com.example.thiscode.core.commutity.service.dto.RoomDmInfo;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomUserRepository roomUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        String senderEmail = "senderEmail";
        String senderPassword = "senderPassword";
        String senderNickname = "senderName";

        String receiverAEmail = "receiverAEmail";
        String receiverAPassword = "receiverAPassword";
        String receiverANickname = "receiverAName";

        String receiverBEmail = "receiverBEmail";
        String receiverBPassword = "receiverBPassword";
        String receiverBNickname = "receiverBAName";

        sender = userRepository.save(new User(senderEmail, senderPassword, senderNickname, "introduction"));
        receiverA = userRepository.save(new User(receiverAEmail, receiverAPassword, receiverANickname, "introduction"));
        receiverB = userRepository.save(new User(receiverBEmail, receiverBPassword, receiverBNickname, "introduction"));
    }

    @AfterEach
    public void tearDown() {
        roomUserRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    private User sender;
    private User receiverA;
    private User receiverB;

    @Transactional
    @DisplayName("DM 방을 생성할 수 있다.")
    @Test
    public void createDmRoom() {
        //given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();

        //when
        Room room = roomService.createDmRoom(senderId, receiverId);

        //then
        Room result = roomRepository.findById(room.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(result).isNotNull();
        List<RoomUser> roomUsers = result.getRoomUsers();
        assertThat(roomUsers.size()).isEqualTo(2);
        assertThat(roomUsers.get(0).getUserId()).isEqualTo(senderId);
        assertThat(roomUsers.get(1).getUserId()).isEqualTo(receiverId);
    }

    @DisplayName("상대방과 DM방이 있다면, DM 방을 생성할 수 없다.")
    @Test
    public void createDmRoomInError() {
        //given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();

        //when
        roomService.createDmRoom(senderId, receiverId);

        //then
        assertThatThrownBy(() -> roomService.createDmRoom(senderId, receiverId))
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("이미 상대방과의 방이 존재합니다.");
        assertThatThrownBy(() -> roomService.createDmRoom(receiverId, senderId))
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("이미 상대방과의 방이 존재합니다.");
    }

    @DisplayName("자기 자신과의 방을 생성할 수 없다.")
    @Test
    public void createDmRoomInError2() {
        //given
        Long senderId = sender.getId();

        //when
        //then
        assertThatThrownBy(() -> roomService.createDmRoom(senderId, senderId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기 자신과의 방을 만들 수 없습니다.");
    }

    @DisplayName("DM 방 목록을 가져올 수 있다.")
    @Test
    public void getRoomList() {
        //given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        Long receiverId2 = receiverB.getId();

        roomService.createDmRoom(senderId, receiverId);
        roomService.createDmRoom(senderId, receiverId2);

        //when
        List<RoomDmInfo> result = roomService.getRoomList(senderId);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRoomId()).isNotNull();
        assertThat(result.get(0).getOtherUserNickname()).isEqualTo(receiverA.getNickname());
        assertThat(result.get(1).getRoomId()).isNotNull();
        assertThat(result.get(1).getOtherUserNickname()).isEqualTo(receiverB.getNickname());
    }

    @DisplayName("DM 방을 나갈 수 있다.")
    @Test
    public void exitDmRoom() {
        // given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        Room room = roomService.createDmRoom(senderId, receiverId);
        Long roomId = room.getId();

        // when
        roomService.exitDmRoom(senderId, roomId);

        // then
        assertThat(roomUserRepository.findByRoomIdAndUserId(roomId, senderId).isEmpty()).isTrue();
    }

    @DisplayName("방에 아무도 없으면 방을 삭제한다.")
    @Test
    public void deleteRoomIfThereIsNobody() {
        // given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        Room room = roomService.createDmRoom(senderId, receiverId);
        Long roomId = room.getId();

        // when
        roomService.exitDmRoom(senderId, roomId);
        roomService.exitDmRoom(receiverId, roomId);

        // then
        assertThat(roomRepository.findById(roomId).isEmpty()).isTrue();
    }

}
