package com.example.thiscode.domain.chat.event;

import com.example.thiscode.domain.commutity.event.RoomEventType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RoomEvent {

    // TODO This property will be used after persistence is available.
//    private Long eventId;
    private RoomEventType eventType;
    private Long roomId;
    private Long userId;
    private LocalDateTime occurrenceDateTime;

}
