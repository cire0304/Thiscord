package com.example.thiscode.domain.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInfoDTO {

    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime sentDateTime;

}
