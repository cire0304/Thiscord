package com.example.thiscode.domain.notification.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInfoDTO {

    private Long roomId;
    private Long senderId;
    private String content;
    private String sentDateTime;

}
