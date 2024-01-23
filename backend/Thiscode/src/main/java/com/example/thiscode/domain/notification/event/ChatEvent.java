package com.example.thiscode.domain.notification.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatEvent {

//    private Long eventId;
    private String eventType;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime occurrenceDateTime;

}
