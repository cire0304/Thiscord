package com.example.thiscode.config.chat;

import com.example.thiscode.domain.chat.service.ChatMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Profile({"local"})
@Configuration
public class ChatRedisConfig {

    public static final String CHAT_CHANNEL_TOPIC = "chat";

    @Bean
    public MessageListenerAdapter chatListenerAdapter(RedisMessageListenerContainer redisMessageListenerContainer,
                                                      ChatMessageSubscriber subscriber) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(subscriber, "onMessage");
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, ChatChannelTopic());
        return messageListenerAdapter;
    }

    @Bean
    public ChannelTopic ChatChannelTopic() {
        return new ChannelTopic(CHAT_CHANNEL_TOPIC);
    }

}
