package com.example.thiscode.domain.chat.entity;

import com.example.thiscode.domain.chat.entity.type.MessageType;
import com.example.thiscode.domain.common.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Id;
import lombok.*;
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
