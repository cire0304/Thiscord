package com.example.thiscode.core.user.controller;


import com.example.thiscode.config.SecurityConfig;
import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.service.UserService;
import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxUserDetailsService;
import com.example.thiscode.security.common.CommonAuthenticationEntryPoint;
import com.example.thiscode.security.common.CommonAuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
@Import({SecurityConfig.class, JwtTokenProvider.class, CommonAuthenticationEntryPoint.class, CommonAuthenticationSuccessHandler.class, AjaxAuthenticationFailureHandler.class, AjaxAuthenticationProvider.class,  AjaxUserDetailsService.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입을 한다.")
    @Test
    public void signIn() throws Exception {
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
                .andExpect(content().string("success"));
    }

    @DisplayName("회원가입할 때, 이메일, 비밀번호, 닉네임 중 하나라도 빈 값이면 에러를 반환한다.")
    @Test
    public void signInError() throws Exception {
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
                .andExpect(content().string("이메일은 필수입니다."));
    }

}