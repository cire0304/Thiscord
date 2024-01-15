package com.example.thiscode.domain.chat.service.dto;

import com.example.thiscode.domain.chat.client.dto.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessageDTO {


    private MessageInfo message;
    private UserInfo user;

    public ChatMessageDTO(MessageInfo message, UserInfo user) {
        this.message = message;
        this.user = user;
    }

}
