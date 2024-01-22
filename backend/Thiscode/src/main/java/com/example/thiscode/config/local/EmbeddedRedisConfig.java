package com.example.thiscode.config.local;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

/**
 * This config is for local and test environment only.
 * When deploying this app to the server, change this config.
 */
@Profile({"local"})
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private String redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(Integer.parseInt(redisPort));
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}
