package com.example.thiscode.domain.notification.service;

import com.example.thiscode.domain.notification.entity.Profile;
import com.example.thiscode.domain.notification.repository.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProfileRepository profileRepository;

    @DisplayName("사용자의 FCM Token 값을 저장한다.")
    @Test
    public void saveFcmToken () {
        //given
        Long userId = 1L;
        String fcmToken = "test fcmToken";
        LocalDateTime now = LocalDateTime.now();
        notificationService.putProfile(userId, fcmToken, now);

        //when
        List<Profile> profiles = profileRepository.findAllByUserId(userId);

        //then
        assertThat(profiles.size()).isEqualTo(1);
        assertThat(profiles.get(0).getFcmToken()).isEqualTo(fcmToken);
     }

    @DisplayName("사용자의 FCM Token 값을 갱신한다.")
    @Test
    public void updateFcmToken () {
        //given
        Long userId = 1L;
        String fcmToken = "test fcmToken";
        LocalDateTime now = LocalDateTime.now();
        notificationService.putProfile(userId, fcmToken, now);


        LocalDateTime after = now.plusDays(1).truncatedTo(ChronoUnit.SECONDS);
        //when
        notificationService.putProfile(userId, fcmToken, after);
        List<Profile> profiles = profileRepository.findAllByUserId(userId);

        //then
        assertThat(profiles.size()).isEqualTo(1);
        assertThat(profiles.get(0).getFcmToken()).isEqualTo(fcmToken);
        assertThat(profiles.get(0).getLastAccessAt()).isEqualTo(after);
    }

}
