package com.example.thiscode.chat.entity;

import com.example.thiscode.chat.entity.type.MessageType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatMessage")
public class ChatMessage {

    @Id
    private String id;
    private Long roomId;
    private Long senderId;
    private String content;
    private MessageType messageType;
    private LocalDateTime sentDateTime;

}
