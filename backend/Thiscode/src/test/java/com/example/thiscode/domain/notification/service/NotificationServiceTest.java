package com.example.thiscode.domain.notification.service;

import com.example.thiscode.domain.commutity.entity.Room;
import com.example.thiscode.domain.commutity.entity.RoomUser;
import com.example.thiscode.domain.commutity.repository.RoomRepository;
import com.example.thiscode.domain.commutity.repository.RoomUserRepository;
import com.example.thiscode.domain.notification.entity.Profile;
import com.example.thiscode.domain.notification.repository.ProfileRepository;
import com.example.thiscode.domain.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private RoomUserRepository roomUserRepository;
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void tearDown() {
        roomUserRepository.deleteAll();
        roomRepository.deleteAll();
        profileRepository.deleteAll();
    }

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

    @DisplayName("방에 참여한 유저의 프로필을 조회할 수 있다.")
    @Test
    public void getProfileByRoomId() {
        Long userIdA = 1L;
        Long userIdB = 2L;
        Room room = Room.createDmRoom();
        roomRepository.save(room);

        RoomUser roomUser1 = new RoomUser(room, userIdA);
        RoomUser roomUser2 = new RoomUser(room, userIdB);
        roomUserRepository.save(roomUser1);
        roomUserRepository.save(roomUser2);

        Profile profileA = new Profile(userIdA, "fcmToken", LocalDateTime.now());
        Profile profileB = new Profile(userIdB, "fcmToken", LocalDateTime.now());
        profileRepository.save(profileA);
        profileRepository.save(profileB);

        List<Profile> result = notificationService.getProfileByRoomId(room.getId());
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(List.of(
                profileA,
                profileB
        ));
    }

    @DisplayName("방에 참여하지 않은 유저의 프로필은 조회되지 않는다.")
    @Test
    public void getProfileByRoomId2() {

        Long userIdA = 1L;
        Long userIdB = 2L;
        Room room = Room.createDmRoom();
        roomRepository.save(room);

        RoomUser roomUser1 = new RoomUser(room, userIdA);
        RoomUser roomUser2 = new RoomUser(room, userIdB);
        roomUser2.exit();

        roomUserRepository.save(roomUser1);
        roomUserRepository.save(roomUser2);

        Profile profileA = new Profile(userIdA, "fcmToken", LocalDateTime.now());
        Profile profileB = new Profile(userIdB, "fcmToken", LocalDateTime.now());
        profileRepository.save(profileA);
        profileRepository.save(profileB);

        List<Profile> profiles = notificationService.getProfileByRoomId(room.getId());
        assertThat(profiles.size()).isEqualTo(1);
    }

}
