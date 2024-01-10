package com.example.thiscode.core.user.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendRequestsDto {

    List<FriendInfoDto> receivedFriendRequests;
    List<FriendInfoDto> sentFriendRequests;

}
