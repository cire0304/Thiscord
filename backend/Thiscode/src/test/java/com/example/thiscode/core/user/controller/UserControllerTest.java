package com.example.thiscode.core.user.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.controller.request.UpdateUserRequest;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.service.dto.UserDetailInfoDto;
import com.example.thiscode.security.jwt.JwtSubject;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends CustomControllerTestSupport {

    @DisplayName("회원가입을 한다.")
    @Test
    public void signUp() throws Exception {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        SignUpRequest request = new SignUpRequest(email, password, nickname);

        //when then
        mockMvc.perform(post("/register")
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
        mockMvc.perform(post("/register")
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
        mockMvc.perform(get("/users/me")
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

    @DisplayName("자신의 자세한 정보를 조회한다.")
    @Test
    @Deprecated
    public void getUserDetailInfo() throws Exception {
        //given
        Long userId = 1L;
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        String introduction = "introduction";
        LocalDateTime now = LocalDateTime.now();

        User user = new User(email, password, nickname, introduction);
        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String token = jwtTokenProvider.createJwtToken(principalUser);
        Cookie tokenCookie = new Cookie("TOKEN", token);

        UserDetailInfoDto userDetailInfoDto = new UserDetailInfoDto(userId, email, password, nickname, introduction, now);
        given(userService.getUserDetailInfo(any())).willReturn(userDetailInfoDto);

        //when then
        mockMvc.perform(get("/users/me/detail")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDetailInfoDto.getId()))
                .andExpect(jsonPath("$.email").value(userDetailInfoDto.getEmail()))
                .andExpect(jsonPath("$.nickname").value(userDetailInfoDto.getNickname()))
                .andExpect(jsonPath("$.introduction").value(userDetailInfoDto.getIntroduction()));
    }

    @DisplayName("자신의 정보를 수정한다.")
    @Test
    public void updateUser() throws Exception {
        //given
        Cookie tokenCookie = getDefaultJwtCookie();

        given(userService.updateUser(any(), any(), any())).willReturn(getDefaultUser());

        //when then
        mockMvc.perform(put("/users/me")
                        .cookie(tokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserRequest("updateNickname", "updateIntroduction"))))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(
                        document("user-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

}
