package com.example.thiscode.chat.service;

import com.example.thiscode.chat.dto.ChatMessageDTO;
import com.example.thiscode.chat.entity.ChatMessage;
import com.example.thiscode.chat.entity.type.MessageType;
import com.example.thiscode.chat.repository.ChatMessageRepository;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeTestClass
    public void clearUp () {
        chatMessageRepository.deleteAll();
    }

    @AfterEach
    public void tearDown () {
        chatMessageRepository.deleteAll();
    }

    @DisplayName("채팅방의 메시지를 조회할 수 있다.")
    @Test
    public void getChatMessage () {
        //given
        Long roomId = 1L;
        Integer page = 0;
        Integer size = 50;

        LocalDateTime timeA = LocalDateTime.now();
        User userA = new User("email", "encodedPassword", "nickname", "introduction");
        Long senderIdA = userRepository
                .save(userA)
                .getId();

        LocalDateTime timeB = timeA.plusMinutes(1L);
        User userB = new User("email", "encodedPassword", "nickname", "introduction");
        Long senderIdB = userRepository
                .save(userB)
                .getId();

        ChatMessage chatMessageA = ChatMessage.builder().roomId(roomId).senderId(senderIdA).content("First Chat").messageType(MessageType.TALK).sentDateTime(timeA).build();
        ChatMessage chatMessageB = ChatMessage.builder().roomId(roomId).senderId(senderIdB).content("Second Chat").messageType(MessageType.TALK).sentDateTime(timeB).build();

        chatMessageRepository.save(chatMessageA);
        chatMessageRepository.save(chatMessageB);

        //when
        List<ChatMessageDTO> chatMessages = chatService.getChatMessages(roomId, page, size);

        //then
        assertThat(chatMessages).hasSize(2);
        assertThat(chatMessages.get(0).getMessage().getContent()).isNotEmpty();
        assertThat(chatMessages.get(0).getUser().getNickname()).isNotEmpty();
        assertThat(chatMessages.get(1).getMessage().getContent()).isNotEmpty();
        assertThat(chatMessages.get(1).getUser().getNickname()).isNotEmpty();
     }
}
