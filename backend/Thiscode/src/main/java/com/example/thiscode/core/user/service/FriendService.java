package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.FriendRepository;
import com.example.thiscode.core.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public void requestFriend(Long senderUserId, String name, String code) {
        User receiver = userRepository.findByNicknameAndUserCode(name, code)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        friendRepository.save(new Friend(sender, receiver));
    }

    @Transactional
    public List<Friend> getFriends(Long userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        return friendRepository.findByReceiverIdOrSenderId(userId);
    }

}
