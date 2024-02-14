package com.example.thiscode.domain.notification.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.domain.notification.dto.ProfileInfoDTO;
import com.example.thiscode.domain.notification.service.NotificationService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class NotificationControllerTest extends CustomControllerTestSupport {

    @MockBean
    private NotificationService notificationService;


    @DisplayName("유저의 알림용 프로필을 갱신한다. ")
    @Test
    public void updateNotification() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();
        String fcmToken = "BPr9yaAbuTkr1_qmRzIHw1VfUGKQ1S5ynNlfoYyVZbSb79zb1SfkGBBf2Y9guollMjXDc1K9AI_7aUJaHhKVgOw";

        ProfileInfoDTO request = new ProfileInfoDTO(fcmToken);

        //when
        mockMvc.perform(put("/api/notifications/profiles/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("request success"))
                .andDo(
                        document("notification-profile-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );

    }

}
