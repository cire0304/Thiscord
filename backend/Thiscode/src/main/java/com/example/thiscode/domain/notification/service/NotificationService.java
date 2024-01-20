package com.example.thiscode.domain.notification.service;

import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.notification.dto.NotificationInfoDTO;
import com.example.thiscode.domain.notification.entity.Profile;
import com.example.thiscode.domain.notification.repository.ProfileRepository;
import com.example.thiscode.domain.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final ProfileRepository profileRepository;
    private final FirebaseMessaging firebaseMessaging;

    // TODO: Write test code (How can i test firebaseMessaging.send(message)?)
    /**
     * Messgae send using user's FCM Token
     */
    @Transactional
    public void sendNotification(NotificationInfoDTO notificationInfoDTO) {
        String senderNickname = userRepository.findById(notificationInfoDTO.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."))
                .getNickname();

        // TODO: If receiver is offline, save notification to database and send notification when receiver is online
        List<Long> userIds = roomUserRepository.findAllByRoomId(notificationInfoDTO.getRoomId())
                .stream()
                .map(RoomUser::getUserId)
                .filter(userId -> !userId.equals(notificationInfoDTO.getSenderId()))
                .toList();

        profileRepository.findAllByUserIdIn(userIds)
                .forEach(profile -> {
                    String content = notificationInfoDTO.getContent();
                    String fcmToken = profile.getFcmToken();
                    Message message = getMessage(content, fcmToken, senderNickname);
                    try {
                        firebaseMessaging.send(message);
                    } catch (FirebaseMessagingException e) {
                        log.error("FCM message send error", e);
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Save or update user's FCM Token. If FCM Token is already exist, This code update lastAccessAt.
     * TODO: LastAccessAt is needed to delete old FCM Token. (Delete FCM Token when user is offline or not using app)
     */
    @Transactional
    public void putProfile(Long userId, String fcmToken, LocalDateTime now) {
        Profile userProfile = profileRepository.findAllByUserId(userId)
                .stream()
                .filter(profile -> profile.getFcmToken().equals(fcmToken))
                .findFirst()
                .orElseGet(() -> {
                    Profile profile = new Profile(userId, fcmToken, now);
                    return profileRepository.save(profile);
                });
        userProfile.updateLastAccessAt(now);
    }

    private Message getMessage(String content, String fcmToken, String senderNickname) {
        Notification notification = Notification.builder()
                .setTitle(String.format("%s님이 메시지를 보냈습니다.", senderNickname))
                .setBody(content)
                .build();

        return Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();
    }

}
