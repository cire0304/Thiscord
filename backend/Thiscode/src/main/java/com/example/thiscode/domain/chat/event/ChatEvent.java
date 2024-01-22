package com.example.thiscode.domain.chat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatEvent {

//    private Long eventId;
    private String eventType;
    private ChatData chatData;
    private LocalDateTime occurrenceDateTime;

}
