package com.example.thiscode.user.service;

import com.example.thiscode.user.entity.Friend;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.entity.type.State;
import com.example.thiscode.user.repository.FriendRepository;
import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.service.dto.FriendDTO;
import com.example.thiscode.user.service.dto.FriendRequestsDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// TODO: Valid 클래스를 따로 만들어도 좋을 것 같다.
@RequiredArgsConstructor
@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendConverter friendConverter;

    // TODO: it should not request friend each other, but this code does. fix it.
    @Transactional
    public void requestFriend(Long senderUserId, String receiverNickname, String receiverUserCode) {
        User receiver = userRepository.findByNicknameAndUserCode(receiverNickname, receiverUserCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        friendRepository.findBySenderId(senderUserId)
                .stream()
                .filter(friend -> friend.getReceiver().equals(receiver))
                .findFirst()
                .ifPresentOrElse(friend -> {
                    if (friend.getFriendState().equals(State.ACCEPT))
                        throw new IllegalArgumentException("이미 친구입니다.");
                    else if (friend.getFriendState().equals(State.REQUEST))
                        throw new IllegalArgumentException("이미 친구 요청을 보냈습니다.");
                    else if(friend.getFriendState().equals(State.REJECT))
                        friend.updateState(State.REQUEST);
                    else throw new IllegalArgumentException("데이터 무결성 오류");
                }, () -> friendRepository.save(new Friend(sender, receiver)));
    }

    @Transactional
    public List<FriendDTO> getFriends(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        return friendRepository.findByReceiverIdOrSenderId(userId)
                .stream()
                .filter(friend -> friend.getFriendState().equals(State.ACCEPT))
                .map(friend -> friendConverter.convertToFriendInfoDto(userId, friend))
                .toList();
    }

    // TODO: Is it okay to return sent and received request info together as type FriendRequestsDTO?
    // TODO: consider refactoring this method later
    @Transactional
    public FriendRequestsDTO getFriendRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        List<FriendDTO> receivedFriendRequests = new ArrayList<>();
        List<FriendDTO> sentFriendRequests = new ArrayList<>();

        friendRepository.findByReceiverIdOrSenderId(userId)
                .stream()
                .filter(friend -> friend.getFriendState().equals(State.REQUEST))
                .forEach(friend -> {
                    if (friend.getReceiver().getId().equals(userId)) {
                        receivedFriendRequests.add(friendConverter.convertToFriendInfoDto(userId, friend));
                    } else {
                        sentFriendRequests.add(friendConverter.convertToFriendInfoDto(userId, friend));
                    }
                });

        return new FriendRequestsDTO(receivedFriendRequests, sentFriendRequests);
    }

    @Transactional
    public void updateFriendState(Long userId, Long friendRequestId, State state) {
        Friend friend = friendRepository.findById(friendRequestId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 친구 요청입니다."));
        if (!friend.getFriendState().equals(State.REQUEST)) {
            throw new IllegalArgumentException("이미 처리된 요청입니다.");
        }
        if (!state.equals(State.ACCEPT) && !state.equals(State.REJECT)) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
        if (!friend.getReceiver().getId().equals(userId) && !friend.getSender().getId().equals(userId)) {
            throw new IllegalArgumentException("유효하지 않은 유저가 요청을 보냈습니다.");
        }

        friend.updateState(state);
    }

    // TODO: develop remove friend method
}
