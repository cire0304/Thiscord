package com.example.thiscode.domain.chat.event;

import com.example.thiscode.domain.chat.entity.ChatMessage;
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
     * publish chat event to redis. This event will be consumed by NotificationService.
     */
    public void publish(ChatMessage chatMessage) {
        ChatData chatData = new ChatData(
                chatMessage.getRoomId(),
                chatMessage.getSenderId(),
                chatMessage.getContent());

        LocalDateTime occurrenceDateTime = LocalDateTime.now();
        ChatEvent chatEvent = new ChatEvent(
                EVENT_TYPE,
                chatData,
                occurrenceDateTime);

        redisTemplate.convertAndSend(CHAT_EVENT_CHANNEL, chatEvent);
    }

}
