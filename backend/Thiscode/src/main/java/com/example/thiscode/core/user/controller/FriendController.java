package com.example.thiscode.core.user.controller;

import com.example.thiscode.core.user.controller.request.AddFriendRequest;
import com.example.thiscode.core.user.controller.request.UpdateFriendStateRequest;
import com.example.thiscode.core.user.controller.response.FriendsResponse;
import com.example.thiscode.core.user.service.FriendService;
import com.example.thiscode.core.user.service.dto.FriendDTO;
import com.example.thiscode.core.user.service.dto.FriendRequestsDTO;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/users/me/friends")
    public ResponseEntity<FriendsResponse> getFriends(@AuthenticationPrincipal JwtSubject subject) {
        List<FriendDTO> friends = friendService.getFriends(subject.getId());
        return ResponseEntity.ok(new FriendsResponse(friends));
    }

    @GetMapping("/users/me/friend-requests")
    public ResponseEntity<FriendRequestsDTO> getFriendRequests(@AuthenticationPrincipal JwtSubject subject) {
        FriendRequestsDTO friendRequests = friendService.getFriendRequests(subject.getId());
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/users/me/friends")
    public ResponseEntity<String> addFriend(@RequestBody @Valid AddFriendRequest request,
                                            @AuthenticationPrincipal JwtSubject subject) {
        friendService.requestFriend(subject.getId(), request.getNickname(), request.getUserCode());
        return ResponseEntity.ok("친구 요청을 보냈습니다.");
    }

    @PutMapping("/users/me/friends")
    public ResponseEntity<String> updateFriendState(@RequestBody UpdateFriendStateRequest request,
                                                    @AuthenticationPrincipal JwtSubject subject) {
        friendService.updateFriendState(subject.getId(), request.getId(), request.getState());
        return ResponseEntity.ok("친구 요청을 수락했습니다.");
    }

}
