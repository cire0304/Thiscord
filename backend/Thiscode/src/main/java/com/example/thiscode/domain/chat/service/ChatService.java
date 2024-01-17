package com.example.thiscode.domain.chat.service;

import com.example.thiscode.domain.chat.client.UserClient;
import com.example.thiscode.domain.chat.dto.UserInfoDTO;
import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.repository.ChatMessageRepository;
import com.example.thiscode.domain.chat.dto.ChatMessageDTO;
import com.example.thiscode.domain.chat.dto.MessageInfoDTO;
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
    private final UserClient userClient;

    public List<ChatMessageDTO> getChatMessages(Long roomId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page,size, Sort.by("sentDateTime").descending());
        Page<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId, paging);

        List<Long> userIds = messages.stream()
                .map(ChatMessage::getSenderId)
                .toList();

        // TODO: check if user not found
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
