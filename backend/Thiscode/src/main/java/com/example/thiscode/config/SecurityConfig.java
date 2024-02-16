package com.example.thiscode.config;

import com.example.thiscode.security.ajax.AjaxAuthenticationFailureHandler;
import com.example.thiscode.security.ajax.AjaxAuthenticationProcessingFilter;
import com.example.thiscode.security.ajax.AjaxAuthenticationProvider;
import com.example.thiscode.security.ajax.AjaxAuthenticationSuccessHandler;
import com.example.thiscode.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.example.thiscode.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthenticationEntryPoint commonAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    private final AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;

    @Bean
    public AjaxAuthenticationProcessingFilter ajaxAuthenticationProcessingFilter() throws Exception {
        AjaxAuthenticationProcessingFilter ajaxAuthenticationProcessingFilter = new AjaxAuthenticationProcessingFilter();
        ajaxAuthenticationProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        ajaxAuthenticationProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);

        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(ajaxAuthenticationProvider);
        ajaxAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);

        return ajaxAuthenticationProcessingFilter;
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    SecurityFilterChain oAuth2securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/api/register", "/api/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(config -> config
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .loginProcessingUrl("/api/login/oauth2/code/*")
                        .authorizationEndpoint(config1 -> config1.baseUri("/api/oauth2/authorization"))
                )
                .sessionManagement(config -> config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(commonAuthenticationEntryPoint)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(config -> config.configurationSource(corsConfigurationSource()))
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                .addFilterAfter(ajaxAuthenticationProcessingFilter(), LogoutFilter.class)
                .build();
    }

}
