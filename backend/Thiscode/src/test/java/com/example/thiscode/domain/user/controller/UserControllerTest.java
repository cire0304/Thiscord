package com.example.thiscode.domain.user.controller;

import com.example.thiscode.CustomControllerTestSupport;
import com.example.thiscode.domain.user.controller.request.SignUpRequest;
import com.example.thiscode.domain.user.controller.request.UpdateUserRequest;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.service.dto.UserDTO;
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

}
