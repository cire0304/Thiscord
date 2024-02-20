package com.example.thiscode.chat.service;

import com.example.thiscode.chat.dto.ChatMessageDTO;
import com.example.thiscode.chat.dto.MessageInfoDTO;
import com.example.thiscode.chat.dto.UserInfoDTO;
import com.example.thiscode.chat.entity.ChatMessage;
import com.example.thiscode.chat.event.ChatEventPublisher;
import com.example.thiscode.chat.repository.ChatMessageRepository;
import com.example.thiscode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessagePublisher chatMessagePublisher;
    private final UserRepository userRepository;
    private final ChatEventPublisher chatEventPublisher;

    public void sendMessage(ChatMessage message) {
        chatEventPublisher.publish(message);

        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO chatMessageDTO = convertToChatMessageDTO(savedMessage);
        chatMessagePublisher.sendMessage(chatMessageDTO);
    }

    public void sendExitMessage(ChatMessage message) {
        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageDTO chatMessageDTO = convertToChatMessageDTO(savedMessage);
        chatMessagePublisher.sendMessage(chatMessageDTO);
    }

    private ChatMessageDTO convertToChatMessageDTO(ChatMessage message) {
        UserInfoDTO userInfoDTO = userRepository
                .findById(message.getSenderId())
                .map(user -> new UserInfoDTO(user.getId(), user.getNickname(), user.getUserCode()))
                .orElseThrow();
        MessageInfoDTO messageInfo = new MessageInfoDTO(
                message.getRoomId(),
                message.getContent(),
                message.getMessageType(),
                message.getSentDateTime()
        );
        return new ChatMessageDTO(messageInfo, userInfoDTO);
    }

    public List<ChatMessageDTO> getChatMessages(Long roomId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page,size, Sort.by("sentDateTime").descending());
        Page<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId, paging);

        List<Long> userIds = messages
                .stream()
                .map(ChatMessage::getSenderId)
                .toList();
        Map<Long, UserInfoDTO> userMap = getUserMap(userIds);

        return messages
                .stream()
                .sorted(Comparator.comparing(ChatMessage::getSentDateTime))
                .map(message -> {
                    UserInfoDTO userInfo = userMap.get(message.getSenderId());
                    MessageInfoDTO messageInfo = new MessageInfoDTO(
                            message.getRoomId(),
                            message.getContent(),
                            message.getMessageType(),
                            message.getSentDateTime()
                    );
                    return new ChatMessageDTO(messageInfo, userInfo);
                })
                .toList();
    }

    private Map<Long, UserInfoDTO> getUserMap(List<Long> userIds) {
        Map<Long, UserInfoDTO> userMap = new HashMap<>();
        userRepository
                .findAllById(userIds)
                .forEach(user -> {
                    UserInfoDTO userInfoDTO = new UserInfoDTO(
                            user.getId(),
                            user.getNickname(),
                            user.getUserCode()
                    );
                    userMap.put(user.getId(), userInfoDTO);
                });
        return userMap;
    }

}
