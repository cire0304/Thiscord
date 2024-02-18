package com.example.thiscode.user.service;

import com.example.thiscode.user.entity.Friend;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.entity.type.State;
import com.example.thiscode.user.repository.FriendRepository;
import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.service.FriendService;
import com.example.thiscode.user.service.UserService;
import com.example.thiscode.user.service.dto.FriendDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendRepository friendRepository;


    @BeforeEach
    public void setUp() {
        String senderEmail = "senderEmail";
        String senderPassword = "sender-password";
        String senderNickname = "sender-name";

        String receiverEmail = "receiverEmail";
        String receiverPassword = "receiver-password";
        String receiverNickname = "receiver-name";

        sender = userService.singUp(senderEmail, senderPassword, senderNickname);
        receiver = userService.singUp(receiverEmail, receiverPassword, receiverNickname);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    private User sender;
    private User receiver;

    @DisplayName("친구 요청을 할 수 있다.")
    @Test
    public void requestFriend() {
        //when
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());

        //then
        Friend friend = friendRepository.findByReceiverId(receiver.getId()).get(0);
        assertThat(friend.getSender()).isEqualTo(sender);
        assertThat(friend.getReceiver()).isEqualTo(receiver);
    }

    @DisplayName("반복된 친구 요청을 할 수 없다.")
    @Test
    public void alreadyRequestFriend() {
        // given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());

        //when
        Friend friendRequest = friendRepository.findByReceiverId(receiver.getId()).get(0);
        friendService.updateFriendState(receiver.getId(), friendRequest.getId(), State.ACCEPT);

        //then
        assertThatThrownBy(() -> friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 친구입니다.");
    }

    @DisplayName("친구 목록을 볼 수 있다.")
    @Test
    public void getFriends() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        List<FriendDTO> receivedFriendRequests = friendService.getFriendRequests(receiver.getId())
                .getReceivedFriendRequests();
        FriendDTO friendInfoDto = receivedFriendRequests.get(0);

        friendService.updateFriendState(receiver.getId(), friendInfoDto.getId(), State.ACCEPT);

        //when
        List<FriendDTO> friendsOfReceiver = friendService.getFriends(receiver.getId());
        List<FriendDTO> friendsOfSender = friendService.getFriends(sender.getId());

        //then
        assertThat(friendsOfReceiver).hasSize(1);
        assertThat(friendsOfReceiver.get(0).getNickname()).isEqualTo(sender.getNickname());
        assertThat(friendsOfReceiver.get(0).getUserCode()).isEqualTo(sender.getUserCode());
        assertThat(friendsOfReceiver.get(0).getEmail()).isEqualTo(sender.getEmail());
        assertThat(friendsOfReceiver.get(0).getUserId()).isEqualTo(sender.getId());

        assertThat(friendsOfSender).hasSize(1);
        assertThat(friendsOfSender.get(0).getNickname()).isEqualTo(receiver.getNickname());
        assertThat(friendsOfSender.get(0).getUserCode()).isEqualTo(receiver.getUserCode());
        assertThat(friendsOfSender.get(0).getEmail()).isEqualTo(receiver.getEmail());
        assertThat(friendsOfSender.get(0).getUserId()).isEqualTo(receiver.getId());
    }

    @DisplayName("친구 요청을 수락할 수 있다.")
    @Test
    public void acceptFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        List<FriendDTO> receivedFriendRequests = friendService.getFriendRequests(receiver.getId())
                .getReceivedFriendRequests();
        FriendDTO friendInfoDto = receivedFriendRequests.get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendInfoDto.getId(), State.ACCEPT);

        //then
        List<FriendDTO> friends = friendService.getFriends(receiver.getId());
        assertThat(friends).hasSize(1);
    }

    @DisplayName("거절된 친구 요청은 조회되지 않는다.")
    @Test
    public void rejectFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        List<FriendDTO> receivedFriendRequests = friendService.getFriendRequests(receiver.getId())
                .getReceivedFriendRequests();
        FriendDTO friendInfoDto = receivedFriendRequests.get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendInfoDto.getId(), State.REJECT);

        //then
        assertThat(friendService.getFriends(receiver.getId())).isEmpty();
    }

    @DisplayName("수락되거나 거절된 친구 요청을 다시 수락하거나 거절할 수 없다.")
    @Test
    public void invalidFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        List<FriendDTO> receivedFriendRequests = friendService.getFriendRequests(receiver.getId())
                .getReceivedFriendRequests();
        FriendDTO friendInfoDto = receivedFriendRequests.get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendInfoDto.getId(), State.ACCEPT);

        //then
        assertThatThrownBy(() -> friendService.updateFriendState(receiver.getId(), friendInfoDto.getId(), State.ACCEPT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 처리된 요청입니다.");
    }

}
