package com.example.thiscode;

import com.example.thiscode.commutity.repository.RoomRepository;
import com.example.thiscode.user.entity.Friend;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.repository.FriendRepository;
import com.example.thiscode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Profile("demo")
@RequiredArgsConstructor
@Component
public class demo {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final RoomRepository roomRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        System.out.println("append test data");
        userRepository.deleteAll();
        friendRepository.deleteAll();
        roomRepository.deleteAll();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String demo1 = "a@a.com";
        String demo1P = passwordEncoder.encode("a");
        String demo1N = "friend user 1";

        String demo2 = "b@b.com";
        String demo2P = passwordEncoder.encode("b");
        String demo2N = "friend user 2";

        String demo3 = "demo@demo.com";
        String demo3P = passwordEncoder.encode("demo");
        String demo3N = "demo user";

        User AUser = userRepository.save(new User(demo1, demo1P, demo1N, "introduction"));
        User BUser = userRepository.save(new User(demo2, demo2P, demo2N, "introduction"));
        User CUser = userRepository.save(new User(demo3, demo3P, demo3N, "introduction"));

        Friend friend = new Friend(AUser, BUser);
        Friend friend2 = new Friend(AUser, CUser);
        Friend friend3 = new Friend(BUser, CUser);
        friendRepository.save(friend);
        friendRepository.save(friend2);
        friendRepository.save(friend3);
    }

}
