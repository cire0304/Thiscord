package com.example.thiscode.user.controller;

import com.example.thiscode.user.controller.request.CheckEmailCodeRequest;
import com.example.thiscode.user.controller.request.SendEmailCodeRequest;
import com.example.thiscode.user.controller.request.SignUpRequest;
import com.example.thiscode.user.service.AuthService;
import com.example.thiscode.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/api/register")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.singUp(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok("success");
    }

    @PostMapping("/api/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailCodeRequest request) {
        return ResponseEntity.ok(authService.sendAuthEmail(request.getEmail()));
    }

    @PostMapping("/api/check-email")
    public ResponseEntity<String> checkEmail(@RequestBody CheckEmailCodeRequest request) {
        authService.checkEmail(request.getEmailCode());
        return ResponseEntity.ok("인증에 성공했습니다.");
    }

}
