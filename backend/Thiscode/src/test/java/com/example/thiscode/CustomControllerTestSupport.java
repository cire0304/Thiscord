package com.example.thiscode;

import com.example.thiscode.config.SecurityConfig;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.FriendRepository;
import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.service.FriendService;
import com.example.thiscode.core.user.service.UserService;
import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxAuthenticationSuccessHandler;
import com.example.thiscode.security.ajax.AjaxUserDetailsService;
import com.example.thiscode.security.common.CommonAuthenticationEntryPoint;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import com.example.thiscode.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(RestDocumentationExtension.class)
@Import({SecurityConfig.class, JwtTokenProvider.class, CommonAuthenticationEntryPoint.class, Oauth2AuthenticationSuccessHandler.class, AjaxAuthenticationFailureHandler.class, AjaxAuthenticationSuccessHandler.class, AjaxAuthenticationProvider.class,  AjaxUserDetailsService.class})
public class CustomControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserService userService;
    @MockBean
    protected UserRepository userRepository;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected JwtTokenProvider jwtTokenProvider;
    @MockBean
    protected FriendRepository friendRepository;
    @MockBean
    protected FriendService friendService;

    protected User getDefaultUser() {
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        String introduction = "introduction";
        return new User(email, password, nickname, introduction);
    }

    protected PrincipalUser getDefaultPrincipalUser() {
        User user = getDefaultUser();
        ProviderUser providerUser = new ProviderUser(user);
        return new PrincipalUser(providerUser);
    }

    protected Cookie getDefaultJwtCookie() {
        PrincipalUser principalUser = getDefaultPrincipalUser();
        String token = jwtTokenProvider.createJwtToken(principalUser);
        return new Cookie("TOKEN", token);
    }

}
