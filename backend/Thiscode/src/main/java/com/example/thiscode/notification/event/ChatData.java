package com.example.thiscode.notification.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatData {

    private Long roomId;
    private Long senderId;
    private String content;

}
