package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.entity.type.State;
import com.example.thiscode.core.user.repository.FriendRepository;
import com.example.thiscode.core.user.repository.UserRepository;
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
        friendRepository.findByReceiverId(receiver.getId())
                .ifPresent(friend -> {
                    assertThat(friend.getSender()).isEqualTo(sender);
                    assertThat(friend.getReceiver()).isEqualTo(receiver);
                });
    }

    @DisplayName("반복된 친구 요청을 할 수 없다.")
    @Test
    public void alreadyRequestFriend() {
        // given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());

        //when
        Friend friendRequest = friendRepository.findByReceiverId(receiver.getId()).get();
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

        //when
        List<Friend> friends = friendService.getFriends(receiver.getId());

        //then
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getSender()).isEqualTo(sender);
        assertThat(friends.get(0).getReceiver()).isEqualTo(receiver);
        assertThat(friends.get(0).getFriendState()).isEqualTo(State.REQUEST);
    }

    @DisplayName("친구 요청을 수락할 수 있다.")
    @Test
    public void acceptFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        Friend friendRequest = friendService.getFriends(receiver.getId()).get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendRequest.getId(), State.ACCEPT);

        //then
        Friend result = friendService.getFriends(receiver.getId()).get(0);
        assertThat(result.getFriendState()).isEqualTo(State.ACCEPT);
    }

    @DisplayName("거절된 친구 요청은 조회되지 않는다.")
    @Test
    public void rejectFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        Friend friendRequest = friendService.getFriends(receiver.getId()).get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendRequest.getId(), State.REJECT);

        //then
        assertThat(friendService.getFriends(receiver.getId())).isEmpty();
    }

    @DisplayName("수락되거나 거절된 친구 요청을 다시 처리할 수 없다.")
    @Test
    public void invalidFriendRequest() {
        //given
        friendService.requestFriend(sender.getId(), receiver.getNickname(), receiver.getUserCode());
        Friend friendRequest = friendService.getFriends(receiver.getId()).get(0);

        //when
        friendService.updateFriendState(receiver.getId(), friendRequest.getId(), State.ACCEPT);

        //then
        assertThatThrownBy(() -> friendService.updateFriendState(receiver.getId(), friendRequest.getId(), State.ACCEPT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 처리된 요청입니다.");
    }

}
