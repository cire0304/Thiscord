package com.example.thiscode.domain.chat;

import com.example.thiscode.domain.chat.dto.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessageDTO chatMessageDTO = objectMapper.readValue(publishMessage, ChatMessageDTO.class);
            Long roomId = chatMessageDTO.getMessage().getRoomId();
            messagingTemplate.convertAndSend("/sub/chat/rooms/" + roomId, chatMessageDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}