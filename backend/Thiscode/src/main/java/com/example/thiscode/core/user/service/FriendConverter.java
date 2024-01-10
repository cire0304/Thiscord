package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.service.dto.FriendInfoDto;
import org.springframework.stereotype.Component;

@Component
public class FriendConverter {

    /**
     * Convert Friend Entity to FriendInfoDto
     * If userId is sender's id, return receiver's info
     * If userId is receiver's id, return sender's info
     *
     * @param userId: user's id that request friend info
     * @param friend: friend entity
     * @return FriendInfoDto
     */
    public FriendInfoDto convertToFriendInfoDto(Long userId, Friend friend) {
        if (userId.equals(friend.getSender().getId())) {
            return new FriendInfoDto(
                    friend.getId(),
                    friend.getReceiver().getId(),
                    friend.getReceiver().getEmail(),
                    friend.getReceiver().getNickname(),
                    friend.getReceiver().getUserCode()
            );
        }
        return new FriendInfoDto(
                friend.getId(),
                friend.getSender().getId(),
                friend.getSender().getEmail(),
                friend.getSender().getNickname(),
                friend.getSender().getUserCode());
    }
}

