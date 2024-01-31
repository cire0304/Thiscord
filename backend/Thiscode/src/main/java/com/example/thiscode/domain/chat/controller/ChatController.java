package com.example.thiscode.domain.chat.controller;

import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.service.ChatService;
import com.example.thiscode.domain.chat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/rooms")
    public void message(ChatMessage message) {
        chatService.sendMessage(message);
    }

    @GetMapping("/chat/rooms/{roomId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessage(@PathVariable("roomId") Long roomId,
                                                           @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                           @RequestParam(defaultValue = "50", name = "size") Integer size) {
        return ResponseEntity.ok(chatService.getChatMessages(roomId, page, size));
    }

}
