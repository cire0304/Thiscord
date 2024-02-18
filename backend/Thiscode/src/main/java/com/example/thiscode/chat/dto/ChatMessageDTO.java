package com.example.thiscode.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessageDTO {


    private MessageInfoDTO message;
    private UserInfoDTO user;

    public ChatMessageDTO(MessageInfoDTO message, UserInfoDTO user) {
        this.message = message;
        this.user = user;
    }

}
