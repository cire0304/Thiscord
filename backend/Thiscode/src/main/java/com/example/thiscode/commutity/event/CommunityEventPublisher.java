package com.example.thiscode.commutity.event;

import com.example.thiscode.commutity.entity.RoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
public class CommunityEventPublisher {

    private final String COMMUNITY_EVENT_CHANNEL = "community-event";
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(RoomUser roomUser, RoomEventType roomEventType) {
        LocalDateTime occurrenceDateTime = LocalDateTime.now();
        RoomEvent roomEvent = RoomEvent.builder()
                .roomId(roomUser.getRoom().getId())
                .userId(roomUser.getUserId())
                .eventType(roomEventType)
                .occurrenceDateTime(occurrenceDateTime)
                .build();

        redisTemplate.convertAndSend(COMMUNITY_EVENT_CHANNEL, roomEvent);
    }

}
