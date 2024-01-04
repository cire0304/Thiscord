package com.example.thiscode;

import com.example.thiscode.config.SecurityConfig;
import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxAuthenticationSuccessHandler;
import com.example.thiscode.security.ajax.AjaxUserDetailsService;
import com.example.thiscode.security.common.CommonAuthenticationEntryPoint;
import com.example.thiscode.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Import;

@Import({SecurityConfig.class, JwtTokenProvider.class, CommonAuthenticationEntryPoint.class, Oauth2AuthenticationSuccessHandler.class, AjaxAuthenticationFailureHandler.class, AjaxAuthenticationSuccessHandler.class, AjaxAuthenticationProvider.class,  AjaxUserDetailsService.class})
public class SecurityTest {
}
