package com.example.thiscode.domain.notification.event;

import com.example.thiscode.domain.notification.dto.NotificationInfoDTO;
import com.example.thiscode.domain.notification.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatEventSubscriber implements MessageListener {

    private final NotificationService notificationService;

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    // TODO: Write test code (How can i test firebaseMessaging.send(message)?)
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatEvent chatEvent = objectMapper.readValue(publishMessage, ChatEvent.class);
            ChatData chatData = chatEvent.getChatData();

            NotificationInfoDTO notificationInfoDTO = NotificationInfoDTO
                    .builder()
                    .roomId(chatData.getRoomId())
                    .senderId(chatData.getSenderId())
                    .content(chatData.getContent())
                    .sentDateTime(chatEvent.getOccurrenceDateTime())
                    .build();
            notificationService.sendNotification(notificationInfoDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
