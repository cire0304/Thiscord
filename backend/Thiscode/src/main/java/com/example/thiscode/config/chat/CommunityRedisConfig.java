package com.example.thiscode.config.chat;

import com.example.thiscode.domain.chat.event.RoomEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Redis configuration of chat server for community event.
 */
@Profile({"local"})
@Configuration
public class CommunityRedisConfig {

    public static final String COMMUNITY_EVENT_CHANNEL = "community-event";

    @Bean
    public MessageListenerAdapter communityListenerAdapter(RedisMessageListenerContainer redisMessageListenerContainer,
                                                           RoomEventSubscriber subscriber) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(subscriber, "onMessage");
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, communityChannelTopic());
        return messageListenerAdapter;
    }

    @Bean
    public ChannelTopic communityChannelTopic() {
        return new ChannelTopic(COMMUNITY_EVENT_CHANNEL);
    }

}
