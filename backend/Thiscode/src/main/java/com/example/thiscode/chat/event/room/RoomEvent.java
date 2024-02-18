package com.example.thiscode.chat.event.room;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RoomEvent {

    private RoomEventType eventType;
    private Long roomId;
    private Long userId;
    private LocalDateTime occurrenceDateTime;

}
