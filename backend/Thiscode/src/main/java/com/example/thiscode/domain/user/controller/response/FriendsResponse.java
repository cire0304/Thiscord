package com.example.thiscode.domain.user.controller.response;

import com.example.thiscode.domain.user.service.dto.FriendDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendsResponse {

    List<FriendDTO> friends;

}
