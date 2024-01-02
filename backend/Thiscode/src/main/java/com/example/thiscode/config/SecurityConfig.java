package com.example.thiscode.config;

import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProcessingFilter;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxUserDetailsService;
import com.example.thiscode.security.common.CommonAuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthenticationEntryPoint commonAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CommonAuthenticationSuccessHandler commonAuthenticationSuccessHandler;

    private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;

    @Bean
    public AjaxAuthenticationProcessingFilter ajaxAuthenticationProcessingFilter() throws Exception {
        AjaxAuthenticationProcessingFilter ajaxAuthenticationProcessingFilter = new AjaxAuthenticationProcessingFilter();
        ajaxAuthenticationProcessingFilter.setAuthenticationSuccessHandler(commonAuthenticationSuccessHandler);
        ajaxAuthenticationProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);

        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(ajaxAuthenticationProvider);
        ajaxAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);

        return ajaxAuthenticationProcessingFilter;
    }

    @Bean
    SecurityFilterChain oAuth2securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2Login(config -> config
                        .successHandler(commonAuthenticationSuccessHandler)
                )
                .sessionManagement(config -> config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(commonAuthenticationEntryPoint)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                .addFilterAfter(ajaxAuthenticationProcessingFilter(), LogoutFilter.class)
                .build();
    }

}
