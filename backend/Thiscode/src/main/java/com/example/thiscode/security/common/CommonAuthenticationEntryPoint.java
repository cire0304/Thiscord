package com.example.thiscode.security.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CommonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // TODO: 나중에 환경 변수값으로 빼내기
    private final String REDIRECT_URL = "http://localhost:80/login";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("error message : {}", authException.getMessage());
        response.sendRedirect(REDIRECT_URL);
    }
}
