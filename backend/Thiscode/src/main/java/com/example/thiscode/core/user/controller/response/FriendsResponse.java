package com.example.thiscode.core.user.controller.response;

import com.example.thiscode.core.user.service.dto.FriendInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendsResponse {

    List<FriendInfoDto> friends;

}
