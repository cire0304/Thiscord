package com.example.thiscode.domain.chat.service;

import com.example.thiscode.domain.chat.client.UserClient;
import com.example.thiscode.domain.chat.dto.UserInfoDTO;
import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.entity.type.MessageType;
import com.example.thiscode.domain.chat.repository.ChatMessageRepository;
import com.example.thiscode.domain.chat.dto.ChatMessageDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @MockBean
    private UserClient userClient;

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
        Long senderIdA = 1L;

        LocalDateTime timeB = timeA.plusMinutes(1L);
        Long senderIdB = 2L;

        ChatMessage chatMessageA = ChatMessage.builder().roomId(roomId).senderId(senderIdA).content("First Chat").messageType(MessageType.TALK).sentDateTime(timeA.toString()).build();
        ChatMessage chatMessageB = ChatMessage.builder().roomId(roomId).senderId(senderIdB).content("Second Chat").messageType(MessageType.TALK).sentDateTime(timeB.toString()).build();

        chatMessageRepository.save(chatMessageA);
        chatMessageRepository.save(chatMessageB);

        given(userClient.getUserMap(any()))
                .willReturn(Map.of(senderIdA, new UserInfoDTO(senderIdA, "nicknameA",  "123456"), senderIdB, new UserInfoDTO(senderIdB, "nicknameB", "654321")));

        //when
        List<ChatMessageDTO> chatMessages = chatService.getChatMessages(roomId, page, size);

        //then
        assertThat(chatMessages).hasSize(2);
        assertThat(chatMessages.get(0).getMessage().getContent()).isEqualTo("First Chat");
        assertThat(chatMessages.get(0).getUser().getNickname()).isEqualTo("nicknameA");
        assertThat(chatMessages.get(1).getMessage().getContent()).isEqualTo("Second Chat");
        assertThat(chatMessages.get(1).getUser().getNickname()).isEqualTo("nicknameB");
     }

}
