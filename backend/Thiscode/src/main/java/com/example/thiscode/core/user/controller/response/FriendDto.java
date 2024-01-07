package com.example.thiscode.core.user.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendDto {

        private Long id;
        private Long senderId;
        private String senderNickname;
        private String senderUserCode;

        private Long receiverId;
        private String receiverNickname;
        private String receiverUserCode;

        private String state;

}
