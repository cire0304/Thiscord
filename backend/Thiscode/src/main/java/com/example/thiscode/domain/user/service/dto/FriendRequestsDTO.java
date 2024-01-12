package com.example.thiscode.domain.user.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendRequestsDTO {

    List<FriendDTO> receivedFriendRequests;
    List<FriendDTO> sentFriendRequests;

}
