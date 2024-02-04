package com.example.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
