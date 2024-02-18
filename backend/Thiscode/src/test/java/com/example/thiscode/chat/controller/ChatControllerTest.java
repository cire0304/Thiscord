package com.example.thiscode.chat.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.chat.dto.ChatMessageDTO;
import com.example.thiscode.chat.dto.MessageInfoDTO;
import com.example.thiscode.chat.dto.UserInfoDTO;
import com.example.thiscode.chat.entity.type.MessageType;
import com.example.thiscode.chat.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ChatControllerTest extends CustomControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @DisplayName("채팅방의 채팅내역을 가져온다.")
    @Test
    void getMessage() throws Exception {
        // given
        List<ChatMessageDTO> chatMessages = new ArrayList<>();
        MessageInfoDTO messageInfoA = new MessageInfoDTO(1L, "First chat", MessageType.TALK, LocalDateTime.now());
        UserInfoDTO UserInfoA = new UserInfoDTO(1L, "First nickname", "123456");
        MessageInfoDTO messageInfoB = new MessageInfoDTO(1L, "Second chat", MessageType.TALK, LocalDateTime.now());
        UserInfoDTO UserInfoB = new UserInfoDTO(2L, "Second nickname", "654321");
        chatMessages.add(new ChatMessageDTO(messageInfoA, UserInfoA));
        chatMessages.add(new ChatMessageDTO(messageInfoB, UserInfoB));

        given(chatService.getChatMessages(any(), any(), any()))
                .willReturn(chatMessages);

        // when then
        mockMvc.perform(get("/api/chat/rooms/{roomId}?page=0&size=20", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(getDefaultJwtCookie())
                )
                .andExpect(status().isOk());
    }
}
