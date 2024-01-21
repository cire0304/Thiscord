package com.example.thiscode.domain.notification.service;

import com.example.thiscode.domain.notification.dto.NotificationInfoDTO;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationSender {

    private final FirebaseMessaging firebaseMessaging;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotification(NotificationInfoDTO notificationInfoDTO, String fcmToken, String senderNickname) {
        try {
            Message message = getMessage(notificationInfoDTO, fcmToken, senderNickname);
            firebaseMessaging.send(message);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            log.error("Fail to send notification: {}", notificationInfoDTO);
        } catch (Exception e) {
            log.error("FCM message send error", e);
            throw new RuntimeException(e);
        }
    }

    private Message getMessage(NotificationInfoDTO notificationInfoDTO, String fcmToken, String senderNickname) throws JsonProcessingException {
        MessageResponse messageResponse = new MessageResponse(notificationInfoDTO, senderNickname);
        Notification notification = Notification.builder()
                .setTitle("새로운 메시지가 도착했습니다.")
                .setBody(objectMapper.writeValueAsString(messageResponse))
                .build();
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();
    }

    @Getter
    protected class MessageResponse {
        Long roomId;
        Long senderId;
        String senderNickname;
        String content;

        public MessageResponse(NotificationInfoDTO notificationInfoDTO, String senderNickname) {
            this.roomId = notificationInfoDTO.getRoomId();
            this.senderId = notificationInfoDTO.getSenderId();
            this.senderNickname = senderNickname;
            this.content = notificationInfoDTO.getContent();
        }
    }

}
