package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.service.dto.FriendDTO;
import org.springframework.stereotype.Component;

@Component
public class FriendConverter {

    /**
     * Convert Friend Entity to FriendDTO
     * If userId is sender's id, return receiver's info
     * If userId is receiver's id, return sender's info
     *
     * @param userId: user's id that request friend info
     * @param friend: friend entity
     * @return FriendDTO
     */
    public FriendDTO convertToFriendInfoDto(Long userId, Friend friend) {
        if (userId.equals(friend.getSender().getId())) {
            return new FriendDTO(
                    friend.getId(),
                    friend.getReceiver().getId(),
                    friend.getReceiver().getEmail(),
                    friend.getReceiver().getNickname(),
                    friend.getReceiver().getUserCode()
            );
        }
        return new FriendDTO(
                friend.getId(),
                friend.getSender().getId(),
                friend.getSender().getEmail(),
                friend.getSender().getNickname(),
                friend.getSender().getUserCode());
    }
}

