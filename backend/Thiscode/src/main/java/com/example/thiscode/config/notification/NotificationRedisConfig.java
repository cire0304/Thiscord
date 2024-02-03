package com.example.thiscode.config.notification;

import com.example.thiscode.domain.chat.service.ChatMessageSubscriber;
import com.example.thiscode.domain.notification.event.ChatEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class NotificationRedisConfig {

    public static final String CHAT_EVENT_CHANNEL = "chat-event";

    @Bean
    public MessageListenerAdapter notificationListenerAdapter(RedisMessageListenerContainer redisMessageListenerContainer,
                                                      ChatEventSubscriber subscriber) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(subscriber, "onMessage");
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, notificationChannelTopic());
        return messageListenerAdapter;
    }

    @Bean
    public ChannelTopic notificationChannelTopic() {
        return new ChannelTopic(CHAT_EVENT_CHANNEL);
    }

}
