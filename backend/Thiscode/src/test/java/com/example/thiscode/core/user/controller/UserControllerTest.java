package com.example.thiscode.core.user.controller;


import com.example.thiscode.CustomTestSupport;
import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.controller.request.UpdateRequest;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.service.UserService;
import com.example.thiscode.core.user.service.dto.UserDetailInfoDto;
import com.example.thiscode.security.jwt.JwtSubject;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends CustomTestSupport {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
                        document("sign-up",
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
                        document("sign-up-error",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("자신의 정보를 조회한다.")
    @Test
    public void getUserInfo() throws Exception {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        String introduction = "introduction";

        User user = new User(email, password, nickname, introduction);
        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String token = jwtTokenProvider.createToken(principalUser);
        Cookie tokenCookie = new Cookie("TOKEN", token);

        //when then
        mockMvc.perform(get("/users/me")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new JwtSubject(principalUser))))
                .andDo(
                        document("get-user-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );;
    }

    @DisplayName("자신의 자세한 정보를 조회한다.")
    @Test
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
        String token = jwtTokenProvider.createToken(principalUser);
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
                .andExpect(jsonPath("$.introduction").value(userDetailInfoDto.getIntroduction()))
                .andDo(
                        document("get-user-detail-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );

    }

    @DisplayName("자신의 정보를 수정한다.")
    @Test
    public void updateUser() throws Exception {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        String introduction = "introduction";

        User user = new User(email, password, nickname, introduction);
        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String token = jwtTokenProvider.createToken(principalUser);
        Cookie tokenCookie = new Cookie("TOKEN", token);

        //when then
        mockMvc.perform(put("/users/me")
                        .cookie(tokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateRequest("updateNickname", "updateIntroduction"))))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(
                        document("update-user",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

}
