package com.example.thiscode.user.controller;

import com.example.thiscode.user.controller.request.AddFriendRequest;
import com.example.thiscode.user.controller.request.UpdateFriendStateRequest;
import com.example.thiscode.user.controller.request.UpdateUserRequest;
import com.example.thiscode.user.controller.response.FriendsResponse;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.service.FriendService;
import com.example.thiscode.user.service.UserService;
import com.example.thiscode.security.jwt.JwtSubject;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import com.example.thiscode.user.service.dto.FriendDTO;
import com.example.thiscode.user.service.dto.FriendRequestsDTO;
import com.example.thiscode.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final FriendService friendService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/me")
    public ResponseEntity<JwtSubject> getUser(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/me")
    public ResponseEntity<JwtSubject> updateUser(
            @AuthenticationPrincipal JwtSubject subject,
            @RequestBody @Valid UpdateUserRequest request,
            HttpServletResponse response
    ) {
        User user = userService.updateUser(subject.getId(), request.getNickname(), request.getIntroduction());

        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String jwtToken = jwtTokenProvider.createJwtToken(principalUser);

        Cookie jwtCookie = CookieUtils.createJwtCookie(jwtToken);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok(new JwtSubject(principalUser));
    }

    @GetMapping("/me/friends")
    public ResponseEntity<FriendsResponse> getFriends(@AuthenticationPrincipal JwtSubject subject) {
        List<FriendDTO> friends = friendService.getFriends(subject.getId());
        return ResponseEntity.ok(new FriendsResponse(friends));
    }

    @GetMapping("/me/friend-requests")
    public ResponseEntity<FriendRequestsDTO> getFriendRequests(@AuthenticationPrincipal JwtSubject subject) {
        FriendRequestsDTO friendRequests = friendService.getFriendRequests(subject.getId());
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/me/friends")
    public ResponseEntity<String> requestFriend(@RequestBody @Valid AddFriendRequest request,
                                                @AuthenticationPrincipal JwtSubject subject) {
        friendService.requestFriend(subject.getId(), request.getNickname(), request.getUserCode());
        return ResponseEntity.ok("친구 요청을 보냈습니다.");
    }

    @PutMapping("/me/friends")
    public ResponseEntity<String> updateFriendState(@RequestBody UpdateFriendStateRequest request,
                                                    @AuthenticationPrincipal JwtSubject subject) {
        friendService.updateFriendState(subject.getId(), request.getId(), request.getState());
        return ResponseEntity.ok("요청을 처리했습니다.");
    }

}
