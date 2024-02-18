package com.example.thiscode.chat.service;

import com.example.thiscode.chat.dto.ChatMessageDTO;
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

    /**
     * publish chat message to redis. This message will be consumed by ChatMessageSubscriber.
     */
    public void sendMessage(ChatMessageDTO message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
