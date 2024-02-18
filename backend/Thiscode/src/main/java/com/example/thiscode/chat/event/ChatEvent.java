package com.example.thiscode.chat.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatEvent {

    private String eventType;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime occurrenceDateTime;

}
