package com.example.thiscode.domain.chat.service;

import com.example.thiscode.domain.chat.client.UserClient;
import com.example.thiscode.domain.chat.dto.UserInfoDTO;
import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.dto.ChatMessageDTO;
import com.example.thiscode.domain.chat.dto.MessageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.thiscode.config.chat.ChatRedisConfig.CHAT_CHANNEL_TOPIC;

@RequiredArgsConstructor
@Component
public class ChatMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic = new ChannelTopic(CHAT_CHANNEL_TOPIC);

    private final UserClient userClient;


    /**
     * publish chat message to redis. This message will be consumed by ChatMessageSubscriber.
     */
    public void sendMessage(ChatMessage message) {
        ChatMessageDTO chatMessageDTO = convertToChatMessageDTO(message);
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageDTO);
    }

    private ChatMessageDTO convertToChatMessageDTO(ChatMessage message) {
        // TODO: check i need to get user Nickname. this code use http communication. it's not good.
        UserInfoDTO userInfo = userClient.getUserMap(List.of(message.getSenderId()))
                .get(message.getSenderId());
        MessageInfoDTO messageInfo = new MessageInfoDTO(message.getRoomId(), message.getContent(), message.getMessageType(), message.getSentDateTime());
        return new ChatMessageDTO(messageInfo, userInfo);
    }

}
