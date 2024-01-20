package com.example.thiscode.domain.chat.client;

import com.example.thiscode.domain.chat.dto.NotificationInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.thiscode.domain.chat.entity.ChatMessage;

@Slf4j
@Component
public class NotificationClient {

    @Value("${user.uri}")
    private String BASE_URL;
    @Value("${user.cookie.jwt}")
    private String JWT_COOKIE_VALUE;
    private String REQUEST_URI = "/notifications";
    private String TOKEN = "TOKEN";

    private WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultCookie(TOKEN, JWT_COOKIE_VALUE)
                .build();
    }

    public void sendNotification(ChatMessage message) {
        NotificationInfoDTO notificationInfo = NotificationInfoDTO.builder()
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .sentDateTime(message.getSentDateTime())
                .build();

        try {
            webClient.post()
                    .uri(REQUEST_URI )
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(notificationInfo))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            log.error("Fail to send notification: {}", message);
        }

    }

}
