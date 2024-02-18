package com.example.thiscode.security.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CommonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Value("${spring.security.login-page-uri}")
    private String REDIRECT_URL;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("error message : {}", authException.getMessage());
        response.sendRedirect(REDIRECT_URL);
    }

}
