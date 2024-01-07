package com.example.thiscode.core.user.controller.response;

import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    default FriendDto toGetFriendDto(Friend friend) {
        User sender = friend.getSender();
        User receiver = friend.getReceiver();
        return new FriendDto(
                friend.getId(),
                sender.getId(),
                sender.getNickname(),
                sender.getUserCode(),
                receiver.getId(),
                receiver.getNickname(),
                receiver.getUserCode(),
                friend.getFriendState().toString()
        );
    }

}
