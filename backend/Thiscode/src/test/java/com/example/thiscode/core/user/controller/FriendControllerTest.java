package com.example.thiscode.core.user.controller;

import com.example.thiscode.CustomTestSupport;
import com.example.thiscode.core.user.controller.request.AddFriendRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendControllerTest extends CustomTestSupport {


    @DisplayName("친구 요청을 보낸다.")
    @Test
    public void addFriend() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();

        AddFriendRequest friendRequest = new AddFriendRequest("receiver-nickname", "receiver-user-code");

        //when then
        mockMvc.perform(post("/users/me/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                        .content(objectMapper.writeValueAsString(friendRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("친구 요청을 보냈습니다."))
                .andDo(
                        document("add-friend",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

}
