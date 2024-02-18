package com.example.thiscode.notification.controller;

import com.example.thiscode.notification.dto.ProfileInfoDTO;
import com.example.thiscode.notification.service.NotificationService;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/api/notifications/profiles/me")
    public ResponseEntity<String> updateNotification(
            @AuthenticationPrincipal JwtSubject subject,
            @RequestBody @Valid ProfileInfoDTO request) {
        String fcmToken = request.getFcmToken();
        LocalDateTime now = LocalDateTime.now();
        notificationService.putProfile(subject.getId(), fcmToken, now);
        return ResponseEntity.ok("request success");
    }

}
