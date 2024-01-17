package com.example.thiscode.domain.chat.repository;

import com.example.thiscode.domain.chat.entity.ChatMessage;
import com.example.thiscode.domain.chat.entity.type.MessageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@MockBean(JpaMetamodelMappingContext.class)
@DataMongoTest
class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @DisplayName("저장 테스트")
    @Test
    public void test() {

    }

}
