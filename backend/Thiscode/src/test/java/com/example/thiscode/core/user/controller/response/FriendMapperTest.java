package com.example.thiscode.core.user.controller.response;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FriendMapperTest {

    @Autowired
    private FriendMapper friendMapper;

    @DisplayName("친구 엔티티를 DTO로 변환할 수 있다.")
    @Test
    public void convertEntityToDto() {
        //given
        String senderEmail = "senderEmail";
        String senderPassword = "senderPassword";
        String senderNickname = "senderNickname";
        String introduction = "introduction";

        String receiverEmail = "receiverEmail";
        String receiverPassword = "receiverPassword";
        String receiverNickname = "receiverNickname";

        User sender = new User(senderEmail, senderPassword, senderNickname, introduction);
        User receiver = new User(receiverEmail, receiverPassword, receiverNickname, introduction);
        Friend friend = new Friend(sender, receiver);

        //when
        FriendDto friendDto = friendMapper.toGetFriendDto(friend);

        //then
        assertThat(friendDto.getSenderId()).isEqualTo(sender.getId());
        assertThat(friendDto.getSenderNickname()).isEqualTo(sender.getNickname());
        assertThat(friendDto.getSenderUserCode()).isEqualTo(sender.getUserCode());
        assertThat(friendDto.getReceiverId()).isEqualTo(receiver.getId());
        assertThat(friendDto.getReceiverNickname()).isEqualTo(receiver.getNickname());
        assertThat(friendDto.getReceiverUserCode()).isEqualTo(receiver.getUserCode());
        assertThat(friendDto.getState()).isEqualTo(friend.getFriendState().toString());
    }

}
