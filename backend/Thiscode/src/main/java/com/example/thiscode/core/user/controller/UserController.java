package com.example.thiscode.core.user.controller;

import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.controller.request.UpdateRequest;
import com.example.thiscode.core.user.service.UserService;
import com.example.thiscode.core.user.service.dto.UserDetailInfoDto;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid SignUpRequest request) {
        userService.singUp(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/users/me")
    public ResponseEntity<JwtSubject> getUserInfo(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/users/me/detail")
    public ResponseEntity<UserDetailInfoDto> getUserDetailInfo(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(userService.getUserDetailInfo(subject.getUserId()));
    }

    @PutMapping("/users/me")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal JwtSubject subject,
                                             @RequestBody @Valid UpdateRequest request) {
        userService.updateUser(subject.getUserId(), request.getNickname(), request.getIntroduction());
        return ResponseEntity.ok("success");
    }

}
