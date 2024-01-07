package com.example.thiscode.core.user.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.core.user.controller.request.AddFriendRequest;
import com.example.thiscode.core.user.controller.request.UpdateFriendStateRequest;
import com.example.thiscode.core.user.controller.response.FriendDto;
import com.example.thiscode.core.user.controller.response.FriendsResponse;
import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.entity.type.State;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendControllerTest extends CustomControllerTestSupport {

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
                        document("friend-add",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("친구 목록을 조회한다.")
    @Test
    public void getFriends() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();
        User sender = getDefaultUser();
        User receiver = getDefaultUser();

        Friend friend = new Friend(sender, receiver);
        given(friendService.getFriends(any())).willReturn(List.of(friend));

        List<FriendDto> getFriendDtos = List.of(new FriendDto(friend.getId(), friend.getSender().getId(), friend.getSender().getNickname(), friend.getSender().getUserCode(), friend.getReceiver().getId(), friend.getReceiver().getNickname(), friend.getReceiver().getUserCode(), friend.getFriendState().toString()));
        FriendsResponse expect = new FriendsResponse(getFriendDtos);

        //when then
        mockMvc.perform(get("/users/me/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expect)))
                .andDo(
                        document("friend-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("friends").description("친구 목록"),
                                        fieldWithPath("friends[].id").description("친구 요청 식별자"),
                                        fieldWithPath("friends[].senderId").description("친구 요청 보낸 사람 식별자"),
                                        fieldWithPath("friends[].senderNickname").description("친구 요청 보낸 사람 닉네임"),
                                        fieldWithPath("friends[].senderUserCode").description("친구 요청 보낸 사람 유저 코드"),
                                        fieldWithPath("friends[].receiverId").description("친구 요청 받은 사람 식별자"),
                                        fieldWithPath("friends[].receiverNickname").description("친구 요청 받은 사람 닉네임"),
                                        fieldWithPath("friends[].receiverUserCode").description("친구 요청 받은 사람 유저 코드"),
                                        fieldWithPath("friends[].state").description("친구 요청 상태 [ACCEPT, REQUEST]")
                                )
                        ));
    }

    @DisplayName("친구 요청을 수락한다.")
    @Test
    public void acceptFriendRequest() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();
        User sender = getDefaultUser();
        User receiver = getDefaultUser();

        Friend friend = new Friend(sender, receiver);
        given(friendService.getFriends(any())).willReturn(List.of(friend));

        UpdateFriendStateRequest friendRequest = new UpdateFriendStateRequest(friend.getId(), State.ACCEPT);

        //when then
        mockMvc.perform(put("/users/me/friends", friend.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                        .content(objectMapper.writeValueAsString(friendRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("친구 요청을 수락했습니다."))
                .andDo(
                        document("friend-accept",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").description("친구 요청 식별자"),
                                        fieldWithPath("state").description("[ACCEPT, REJECT]")
                                )
                        )
                );
    }

}
