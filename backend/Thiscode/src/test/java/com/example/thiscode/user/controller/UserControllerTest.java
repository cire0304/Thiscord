package com.example.thiscode.user.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.user.controller.request.AddFriendRequest;
import com.example.thiscode.user.controller.request.SignUpRequest;
import com.example.thiscode.user.controller.request.UpdateFriendStateRequest;
import com.example.thiscode.user.controller.request.UpdateUserRequest;
import com.example.thiscode.user.controller.response.FriendsResponse;
import com.example.thiscode.user.entity.Friend;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.security.jwt.JwtSubject;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import com.example.thiscode.user.entity.type.State;
import com.example.thiscode.user.service.FriendConverter;
import com.example.thiscode.user.service.dto.FriendDTO;
import com.example.thiscode.user.service.dto.FriendRequestsDTO;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends CustomControllerTestSupport {
    @Autowired
    private FriendConverter friendConverter;

    @DisplayName("회원가입을 한다.")
    @Test
    public void signUp() throws Exception {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        SignUpRequest request = new SignUpRequest(email, password, nickname);

        //when then
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(
                        document("user-sign-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );

    }

    @DisplayName("회원가입할 때, 이메일, 비밀번호, 닉네임 중 하나라도 빈 값이면 에러를 반환한다.")
    @Test
    public void signUpError() throws Exception {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        SignUpRequest requestWithoutEmail = new SignUpRequest(null, password, nickname);

        //when then
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithoutEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이메일은 필수입니다."))
                .andDo(
                        document("user-sign-up-error",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("자신의 정보를 조회한다.")
    @Test
    public void getUserInfo() throws Exception {
        //given
        PrincipalUser principalUser = getDefaultPrincipalUser();
        String token = jwtTokenProvider.createJwtToken(principalUser);
        Cookie tokenCookie = new Cookie("TOKEN", token);

        //when then
        mockMvc.perform(get("/api/users/me")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new JwtSubject(principalUser))))
                .andDo(
                        document("user-get-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("자신의 정보를 수정한다.")
    @Test
    public void updateUser() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();

        User user = getDefaultUser();
        given(userService.updateUser(any(), any(), any())).willReturn(user);
        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);


        //when then
        mockMvc.perform(put("/api/users/me")
                        .cookie(tokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserRequest("updateNickname", "updateIntroduction"))))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new JwtSubject(principalUser))))
                .andDo(
                        document("user-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("친구 요청을 보낸다.")
    @Test
    public void addFriend() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();

        AddFriendRequest friendRequest = new AddFriendRequest("receiver-nickname", "receiver-user-code");

        //when then
        mockMvc.perform(post("/api/users/me/friends")
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
        mockMvc.perform(get("/api/users/me/friends")
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
        mockMvc.perform(get("/api/users/me/friend-requests")
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
        mockMvc.perform(put("/api/users/me/friends", friend.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie)
                        .content(objectMapper.writeValueAsString(friendRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("요청을 처리했습니다."))
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
