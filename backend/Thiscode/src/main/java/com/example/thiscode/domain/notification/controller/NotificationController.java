package com.example.thiscode.domain.notification.controller;

import com.example.thiscode.domain.notification.dto.NotificationInfoDTO;
import com.example.thiscode.domain.notification.dto.ProfileInfoDTO;
import com.example.thiscode.domain.notification.service.NotificationService;
import com.example.thiscode.security.jwt.JwtSubject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final NotificationService notificationService;

    // TODO: if room is created, send notification to all users in the room

    @PostMapping("/notifications")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationInfoDTO request) {
        notificationService.sendNotification(request);
        return ResponseEntity.ok("request success");
    }

    @PutMapping("/notifications/profiles/me")
    public ResponseEntity<String> updateNotification(
            @AuthenticationPrincipal JwtSubject subject,
            @RequestBody @Valid ProfileInfoDTO request) {
        String fcmToken = request.getFcmToken();
        LocalDateTime now = LocalDateTime.now();
        notificationService.putProfile(subject.getId(), fcmToken, now);
        return ResponseEntity.ok("request success");
    }

}
