package com.example.thiscode.chat.event;

import com.example.thiscode.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ChatEventPublisher {

    private final String EVENT_TYPE = "CHAT";
    private final String CHAT_EVENT_CHANNEL = "chat-event";
    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * publish chat event to redis. This event will be consumed by NotificationSubscriber.
     */
    public void publish(ChatMessage chatMessage) {
        LocalDateTime occurrenceDateTime = LocalDateTime.now();
        ChatEvent chatEvent = ChatEvent.builder()
                .eventType(EVENT_TYPE)
                .roomId(chatMessage.getRoomId())
                .senderId(chatMessage.getSenderId())
                .content(chatMessage.getContent())
                .occurrenceDateTime(occurrenceDateTime)
                .build();

        redisTemplate.convertAndSend(CHAT_EVENT_CHANNEL, chatEvent);
    }

}
