package com.example.thiscode.notification.event;

import com.example.thiscode.notification.dto.NotificationInfoDTO;
import com.example.thiscode.notification.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatEventSubscriber implements MessageListener {

    private final NotificationService notificationService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    // TODO: Write test code (How can i test firebaseMessaging.send(message)?)
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatEvent chatEvent = objectMapper.readValue(publishMessage, ChatEvent.class);

            NotificationInfoDTO notificationInfoDTO = NotificationInfoDTO
                    .builder()
                    .roomId(chatEvent.getRoomId())
                    .senderId(chatEvent.getSenderId())
                    .content(chatEvent.getContent())
                    .sentDateTime(chatEvent.getOccurrenceDateTime())
                    .build();
            notificationService.sendNotification(notificationInfoDTO);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
