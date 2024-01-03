package com.example.thiscode.core.user.controller;

import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

}
