package com.example.thiscode.notification.controller;

import com.example.thiscode.notification.dto.ProfileInfoDTO;
import com.example.thiscode.notification.service.NotificationService;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/profiles/me")
    public ResponseEntity<String> updateNotification(
            @AuthenticationPrincipal JwtSubject subject,
            @RequestBody @Valid ProfileInfoDTO request) {
        String fcmToken = request.getFcmToken();
        LocalDateTime now = LocalDateTime.now();
        notificationService.putProfile(subject.getId(), fcmToken, now);
        return ResponseEntity.ok("request success");
    }

}
