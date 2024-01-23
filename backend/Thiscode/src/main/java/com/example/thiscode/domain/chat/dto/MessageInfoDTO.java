package com.example.thiscode.domain.chat.dto;

import com.example.thiscode.domain.chat.entity.type.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfoDTO {

    private Long roomId;
    private String content;
    private MessageType messageType;
    private LocalDateTime sentDateTime;

}
