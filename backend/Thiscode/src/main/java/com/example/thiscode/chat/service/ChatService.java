package com.example.thiscode.chat.service;

import com.example.thiscode.chat.client.UserClient;
import com.example.thiscode.chat.dto.ChatMessageDTO;
import com.example.thiscode.chat.dto.MessageInfoDTO;
import com.example.thiscode.chat.dto.UserInfoDTO;
import com.example.thiscode.chat.entity.ChatMessage;
import com.example.thiscode.chat.event.ChatEventPublisher;
import com.example.thiscode.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessagePublisher chatMessagePublisher;
    private final UserClient userClient;
    private final ChatEventPublisher chatEventPublisher;

    public void sendMessage(ChatMessage message) {
        chatEventPublisher.publish(message);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        chatMessagePublisher.sendMessage(savedMessage);
    }

    public void sendExitMessage(ChatMessage message) {
        ChatMessage savedMessage = chatMessageRepository.save(message);
        chatMessagePublisher.sendMessage(savedMessage);
    }

    public List<ChatMessageDTO> getChatMessages(Long roomId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page,size, Sort.by("sentDateTime").descending());
        Page<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId, paging);

        List<Long> userIds = messages.stream()
                .map(ChatMessage::getSenderId)
                .toList();

        Map<Long, UserInfoDTO> userMap = userClient.getUserMap(userIds);

        return messages.stream()
                .sorted(Comparator.comparing(ChatMessage::getSentDateTime))
                .map(message -> {
                    UserInfoDTO userInfo = userMap.get(message.getSenderId());
                    MessageInfoDTO messageInfo = new MessageInfoDTO(message.getRoomId(), message.getContent(), message.getMessageType(), message.getSentDateTime());
                    return new ChatMessageDTO(messageInfo, userInfo);
                }).toList();
    }

}
