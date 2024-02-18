package com.example.thiscode.notification.service;

import com.example.thiscode.notification.dto.NotificationInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// TODO: if room is created, send notification to all users in the room
@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationSender {

    private final FirebaseMessaging firebaseMessaging;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Messgae send using user's FCM Token
     */
    public void sendNotification(NotificationInfoDTO notificationInfoDTO, String fcmToken, String senderNickname) {
        try {
            MessageResponse messageResponse = MessageResponse.builder()
                    .roomId(notificationInfoDTO.getRoomId())
                    .senderId(notificationInfoDTO.getSenderId())
                    .senderNickname(senderNickname)
                    .content(notificationInfoDTO.getContent())
                    .build();

            Notification notification = Notification.builder()
                    .setTitle("새로운 메시지가 도착했습니다.")
                    .setBody(objectMapper.writeValueAsString(messageResponse))
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .build();

            firebaseMessaging.send(message);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            log.error("Fail to send notification: {}", notificationInfoDTO);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("FCM message send error", e);
            throw new RuntimeException(e);
        }
    }

    @Builder
    @Getter
    protected static class MessageResponse {

        Long roomId;
        Long senderId;
        String senderNickname;
        String content;

    }

}
