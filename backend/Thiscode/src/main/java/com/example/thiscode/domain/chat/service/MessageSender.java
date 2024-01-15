package com.example.thiscode.domain.chat.service;

import com.example.thiscode.domain.chat.client.UserClient;
import com.example.thiscode.domain.chat.client.dto.UserInfo;
import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.repository.ChatMessageRepository;
import com.example.thiscode.domain.chat.service.dto.ChatMessageDTO;
import com.example.thiscode.domain.chat.service.dto.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class MessageSender {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;
    private final ChatMessageRepository chatMessageRepository;
    private final UserClient userClient;

    public void sendMessage(ChatMessage message) {
        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO chatMessageDTO = convertToChatMessageDTO(savedMessage);
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageDTO);
    }

    private ChatMessageDTO convertToChatMessageDTO(ChatMessage message) {
        UserInfo userInfo = userClient.getUserMap(List.of(message.getSenderId()))
                .get(message.getSenderId());
        MessageInfo messageInfo = new MessageInfo(message.getRoomId(), message.getContent(), message.getMessageType(), message.getSentDateTime());
        return new ChatMessageDTO(messageInfo, userInfo);
    }

}
