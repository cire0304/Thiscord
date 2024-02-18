package com.example.thiscode.commutity.service;

import com.example.thiscode.commutity.dto.GroupRoomDTO;
import com.example.thiscode.commutity.dto.RoomListDTO;
import com.example.thiscode.commutity.entity.Room;
import com.example.thiscode.commutity.entity.RoomUser;
import com.example.thiscode.commutity.entity.type.RoomUserState;
import com.example.thiscode.commutity.event.CommunityEventPublisher;
import com.example.thiscode.commutity.repository.RoomRepository;
import com.example.thiscode.commutity.repository.RoomUserRepository;
import com.example.thiscode.commutity.dto.DmRoomDTO;
import com.example.thiscode.commutity.dto.RoomUserDTO;
import com.example.thiscode.commutity.service.RoomService;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @MockBean
    CommunityEventPublisher communityEventPublisher;

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

    @Transactional
    @DisplayName("Group 방을 생성할 수 있다.")
    @Test
    public void createGroupRoom() {
        //given
        Long userAId = sender.getId();
        Long userBId = receiverA.getId();
        Long userCId = receiverB.getId();
        String groupName = "groupName";

        //when
        GroupRoomDTO groupRoom = roomService.createGroupRoom(userAId, groupName, List.of(userBId, userCId));

        //then
        Room result = roomRepository.findById(groupRoom.getRoomId())
                .orElseThrow(IllegalArgumentException::new);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(groupName);

        List<RoomUser> roomUsers = result.getRoomUsers();
        assertThat(roomUsers.size()).isEqualTo(3);
        assertThat(roomUsers).extracting("userId")
                .contains(userAId, userBId, userCId);
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
    public void getDmRoomList() {
        //given
        Long senderId = sender.getId();
        Long receiverId = receiverA.getId();
        Long receiverId2 = receiverB.getId();

        DmRoomDTO dmRoomA = roomService.createDmRoom(senderId, receiverId);
        DmRoomDTO dmRoomB = roomService.createDmRoom(senderId, receiverId2);

        //when
        RoomListDTO roomList = roomService.getRoomListByUserId(senderId);

        //then
        List<DmRoomDTO> result = roomList.getDmRooms();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(
                dmRoomA,
                dmRoomB
        ));
    }

    @DisplayName("Group 방 목록을 가져올 수 있다.")
    @Test
    public void getGroupRoomList() {
        //given
        Long userAId = sender.getId();
        Long userBId = receiverA.getId();
        Long userCId = receiverB.getId();
        String groupName = "groupName";

        GroupRoomDTO groupRoom = roomService.createGroupRoom(userAId, groupName, List.of(userBId, userCId));

        //when
        RoomListDTO roomList = roomService.getRoomListByUserId(userAId);

        //then
        List<GroupRoomDTO> groupRoomList = roomList.getGroupRooms();
        assertThat(groupRoomList.size()).isEqualTo(1);
        assertThat(groupRoomList.get(0).getRoomId()).isEqualTo(groupRoom.getRoomId());
        assertThat(groupRoomList.get(0).getGroupName()).isEqualTo(groupName);
        assertThat(groupRoomList.get(0).getRoomUsers()).extracting("userId")
                .contains(userAId, userBId, userCId);
    }

    @DisplayName("Group 방에 유저를 초대할 수 있다.")
    @Test
    public void inviteUserToGroupRoom() {

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
        RoomUserDTO result = roomService.findRoomUser(receiverId, roomId);

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

        roomService.exitRoom(senderId, dmRoom.getRoomId());

        //when
        RoomListDTO roomList = roomService.getRoomListByUserId(senderId);
        List<DmRoomDTO> result = roomList.getDmRooms();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getRoomId()).isNotNull();
        assertThat(result.get(0).getOtherUser().getNickname()).isEqualTo(receiverA.getNickname());
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
        roomService.exitRoom(senderId, roomId);

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
        roomService.exitRoom(senderId, roomId);
        roomService.exitRoom(receiverId, roomId);

        // then
        assertThat(roomRepository.findById(roomId).isEmpty()).isTrue();
    }

    @DisplayName("Group 방에 유저를 초대할 수 있다.")
    @Test
    public void inviteUser() {
        // given
        Long senderAId = sender.getId();
        Long userBId = receiverA.getId();
        Long receiverId = receiverB.getId();
        GroupRoomDTO groupRoom = roomService.createGroupRoom(senderAId, "group name", List.of(userBId));
        Long roomId = groupRoom.getRoomId();

        // when
        roomService.inviteUserToRoom(senderAId, receiverId, roomId);

        // then
        RoomUser roomUser = roomUserRepository.findByRoomIdAndUserId(roomId, receiverId)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(roomUser.getState()).isEqualTo(RoomUserState.INVITE);
    }

}
