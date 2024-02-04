package com.example.chat.event.room;


import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.type.MessageType;
import com.example.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoomEventSubscriber implements MessageListener {

    private final ChatService chatService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * event come from CommunityEventPublisher
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            RoomEvent roomEvent = objectMapper.readValue(publishMessage, RoomEvent.class);

            ChatMessage chatMessage = ChatMessage.builder()
                    .roomId(roomEvent.getRoomId())
                    .senderId(roomEvent.getUserId())
                    .content("유저가 방에서 퇴장하셨습니다.")
                    .messageType(MessageType.EXIT)
                    .sentDateTime(roomEvent.getOccurrenceDateTime())
                    .build();
            chatService.sendExitMessage(chatMessage);
        } catch (Exception e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
