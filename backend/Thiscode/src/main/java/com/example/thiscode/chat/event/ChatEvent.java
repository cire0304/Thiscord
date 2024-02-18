package com.example.thiscode.chat.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatEvent {

    // TODO This property will be used after persistence is available.
//    private Long eventId;
    private String eventType;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime occurrenceDateTime;

}
