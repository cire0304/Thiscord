package com.example.thiscode.core.user.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.core.user.controller.request.AddFriendRequest;
import com.example.thiscode.core.user.controller.request.UpdateFriendStateRequest;
import com.example.thiscode.core.user.controller.response.FriendsResponse;
import com.example.thiscode.core.user.entity.Friend;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.entity.type.State;
import com.example.thiscode.core.user.service.FriendConverter;
import com.example.thiscode.core.user.service.dto.FriendDTO;
import com.example.thiscode.core.user.service.dto.FriendRequestsDTO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FriendConverter friendConverter;

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
        Long senderId = 1L;
        User receiver = getDefaultUser();

        Friend friend = new Friend(sender, receiver);
        FriendDTO friendInfoDto = friendConverter.convertToFriendInfoDto(senderId, friend);
        given(friendService.getFriends(any())).willReturn(List.of(friendInfoDto));

        FriendsResponse expect = new FriendsResponse(List.of(friendInfoDto));

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
                                preprocessResponse(prettyPrint())
                        ));
    }

    @DisplayName("요청하거나 요청된 친구목록을 조회한다.")
    @Test
    public void getFriendsByState() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();
        User user = getDefaultUser();
        Long userId = 1L;
        User otherUserA = getDefaultUser();
        User otherUserB = getDefaultUser();

        Friend sentFriendRequest = new Friend(user, otherUserA);
        Friend ReceivedFriendRequest = new Friend(otherUserB, user);

        FriendDTO sentFriendRequestDto = friendConverter.convertToFriendInfoDto(userId, sentFriendRequest);
        FriendDTO ReceivedFriendRequestDto = friendConverter.convertToFriendInfoDto(userId, ReceivedFriendRequest);

        FriendRequestsDTO friendRequestsDto = new FriendRequestsDTO(List.of(ReceivedFriendRequestDto), List.of(sentFriendRequestDto));
        given(friendService.getFriendRequests(any())).willReturn(friendRequestsDto);

        FriendRequestsDTO expect = friendRequestsDto;

        //when then
        mockMvc.perform(get("/users/me/friend-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expect)))
                .andDo(
                        document("friend-request-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        ));
    }

    @DisplayName("친구 요청을 수락한다.")
    @Test
    public void acceptFriendRequest() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();
        User sender = getDefaultUser();
        Long senderId = 1L;
        User receiver = getDefaultUser();

        Friend friend = new Friend(sender, receiver);
        FriendDTO friendInfoDto = friendConverter.convertToFriendInfoDto(senderId, friend);
        given(friendService.getFriends(any())).willReturn(List.of(friendInfoDto));

        UpdateFriendStateRequest friendRequest = new UpdateFriendStateRequest(friendInfoDto.getId(), State.ACCEPT);

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
