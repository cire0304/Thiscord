package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.FriendRepository;
import com.example.thiscode.core.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("친구 요청을 할 수 있다.")
    @Test
    public void requestFriend() {
        //given
        String senderEmail = "senderEmail";
        String senderPassword = "sender-password";
        String senderNickname = "sender-name";

        String receiverEmail = "receiverEmail";
        String receiverPassword = "receiver-password";
        String receiverNickname = "receiver-name";

        User sender = userService.singUp(senderEmail, senderPassword, senderNickname);
        User receiver = userService.singUp(receiverEmail, receiverPassword, receiverNickname);


        //when
        friendService.requestFriend(sender.getId(), receiverNickname, receiver.getUserCode());

        //then
        friendRepository.findByReceiverId(receiver.getId())
                .ifPresent(friend -> {
                    assertThat(friend.getSender()).isEqualTo(sender);
                    assertThat(friend.getReceiver()).isEqualTo(receiver);
                });
    }

}
