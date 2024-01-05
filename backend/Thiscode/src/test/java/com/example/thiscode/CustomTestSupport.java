package com.example.thiscode;

import com.example.thiscode.config.SecurityConfig;
import com.example.thiscode.core.user.controller.UserController;
import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxAuthenticationSuccessHandler;
import com.example.thiscode.security.ajax.AjaxUserDetailsService;
import com.example.thiscode.security.common.CommonAuthenticationEntryPoint;
import com.example.thiscode.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;

@WebMvcTest(controllers = {UserController.class})
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(RestDocumentationExtension.class)
@Import({SecurityConfig.class, JwtTokenProvider.class, CommonAuthenticationEntryPoint.class, Oauth2AuthenticationSuccessHandler.class, AjaxAuthenticationFailureHandler.class, AjaxAuthenticationSuccessHandler.class, AjaxAuthenticationProvider.class,  AjaxUserDetailsService.class})
public class CustomTestSupport {
}
