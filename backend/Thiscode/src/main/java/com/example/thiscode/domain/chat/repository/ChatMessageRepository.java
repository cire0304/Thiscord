package com.example.thiscode.domain.chat.repository;

import com.example.thiscode.domain.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

}
