package com.example.thiscode.user.controller.response;

import com.example.thiscode.user.service.dto.FriendDTO;
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
