package com.example.thiscode.domain.commutity.service;

import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.entity.type.RoomUserState;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.commutity.dto.DmRoomDTO;
import com.example.thiscode.domain.commutity.dto.RoomUserDTO;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.repository.UserRepository;
import com.example.thiscode.domain.user.service.UserService;
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
        DmRoomDTO dmRoom = roomService.createDmRoom(senderId, receiverId);

        //then
        Room result = roomRepository.findById(dmRoom.getRoomId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(result).isNotNull();
        List<RoomUser> roomUsers = result.getRoomUsers();
        assertThat(roomUsers.size()).isEqualTo(2);
        assertThat(roomUsers.get(0).getUserId()).isEqualTo(senderId);
        assertThat(roomUsers.get(1).getUserId()).isEqualTo(receiverId);
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
        List<DmRoomDTO> result = roomService.getRoomList(senderId);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRoomId()).isNotNull();
        assertThat(result.get(0).getOtherUserId()).isEqualTo(receiverA.getId());
        assertThat(result.get(0).getOtherUserNickname()).isEqualTo(receiverA.getNickname());
        assertThat(result.get(1).getRoomId()).isNotNull();
        assertThat(result.get(1).getOtherUserId()).isEqualTo(receiverB.getId());
        assertThat(result.get(1).getOtherUserNickname()).isEqualTo(receiverB.getNickname());
    }

    @DisplayName("방에 참여한 사용자의 정보를 가져올 수 있다.")
    @Test
    public void findRoomUser() {
        //given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        DmRoomDTO dmRoom = roomService.createDmRoom(senderId, receiverId);
        Long roomId = dmRoom.getRoomId();

        //when
        RoomUserDTO result = roomService.findRoomUser(senderId, roomId, receiverId);

        //then
        assertThat(result.getUserId()).isEqualTo(receiverId);
        assertThat(result.getNickname()).isEqualTo(receiverA.getNickname());
        assertThat(result.getUserCode()).isEqualTo(receiverA.getUserCode());
        assertThat(result.getEmail()).isEqualTo(receiverA.getEmail());
        assertThat(result.getIntroduction()).isEqualTo(receiverA.getIntroduction());
        assertThat(result.getState()).isEqualTo(RoomUserState.JOIN);

    }

    @DisplayName("퇴장한 DM 방은 목록에 나오지 않는다.")
    @Test
    public void getRoomListWithJoin() {
        //given
        Long senderId = sender.getId();
        Long receiverAId = receiverA.getId();
        Long receiverBId = receiverB.getId();
        roomService.createDmRoom(senderId, receiverAId);
        DmRoomDTO dmRoom = roomService.createDmRoom(senderId, receiverBId);

        roomService.exitDmRoom(senderId, dmRoom.getRoomId());

        //when
        List<DmRoomDTO> result = roomService.getRoomList(senderId);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getRoomId()).isNotNull();
        assertThat(result.get(0).getOtherUserNickname()).isEqualTo(receiverA.getNickname());
    }



    @DisplayName("DM 방을 나갈 수 있다.")
    @Test
    public void exitDmRoom() {
        // given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        DmRoomDTO dmRoom = roomService.createDmRoom(senderId, receiverId);
        Long roomId = dmRoom.getRoomId();

        // when
        roomService.exitDmRoom(senderId, roomId);

        // then
        assertThat(roomUserRepository.findByRoomIdAndUserId(roomId, senderId).get().getState())
                .isEqualTo(RoomUserState.EXIT);
    }

    @DisplayName("방에 아무도 없으면 방을 삭제한다.")
    @Test
    public void deleteRoomIfThereIsNobody() {
        // given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        DmRoomDTO dmRoom = roomService.createDmRoom(senderId, receiverId);
        Long roomId = dmRoom.getRoomId();

        // when
        roomService.exitDmRoom(senderId, roomId);
        roomService.exitDmRoom(receiverId, roomId);

        // then
        assertThat(roomRepository.findById(roomId).isEmpty()).isTrue();
    }

}
