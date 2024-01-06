package com.example.thiscode.core.user.controller;

import com.example.thiscode.core.user.controller.request.AddFriendRequest;
import com.example.thiscode.core.user.controller.response.FriendMapper;
import com.example.thiscode.core.user.controller.response.GetFriendDto;
import com.example.thiscode.core.user.controller.response.GetFriendsResponse;
import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.service.FriendService;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/users/me/friends")
    public ResponseEntity<String> addFriend(@RequestBody @Valid AddFriendRequest request,
                                            @AuthenticationPrincipal JwtSubject subject) {
        friendService.requestFriend(subject.getId(), request.getName(), request.getCode());
        return ResponseEntity.ok("친구 요청을 보냈습니다.");
    }

}
