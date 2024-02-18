package com.example.thiscode.notification.service;

import com.example.thiscode.commutity.entity.RoomUser;
import com.example.thiscode.commutity.repository.RoomUserRepository;
import com.example.thiscode.notification.dto.NotificationInfoDTO;
import com.example.thiscode.notification.entity.Profile;
import com.example.thiscode.notification.repository.ProfileRepository;
import com.example.thiscode.user.repository.UserRepository;
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

    private final NotificationSender notificationSender;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final ProfileRepository profileRepository;

    // TODO: If receiver is offline, save notification to database and send notification when receiver is online
    @Transactional
    public void sendNotification(NotificationInfoDTO notificationInfoDTO) {
        Long senderId = notificationInfoDTO.getSenderId();
        String senderNickname = userRepository.findById(notificationInfoDTO.getSenderId())
                .orElseThrow(() -> {
                    log.error("User not found. userId: {}", notificationInfoDTO.getSenderId());
                    return new EntityNotFoundException("User not found");
                })
                .getNickname();

        getProfileByRoomId(notificationInfoDTO.getRoomId())
                .stream()
                .filter(profile -> !profile.getUserId().equals(senderId))
                .forEach(profile -> {
                    String fcmToken = profile.getFcmToken();
                    notificationSender.sendNotification(notificationInfoDTO, fcmToken, senderNickname);
                });
    }

    @Transactional
    public List<Profile> getProfileByRoomId(Long roomId) {
        List<Long> userIds = roomUserRepository.findAllByRoomId(roomId)
                .stream()
                .filter(RoomUser::isJoin)
                .map(RoomUser::getUserId)
                .toList();
        return profileRepository.findAllByUserIdIn(userIds);
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

}
