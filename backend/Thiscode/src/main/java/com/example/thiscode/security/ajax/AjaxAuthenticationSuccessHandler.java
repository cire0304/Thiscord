package com.example.thiscode.security.ajax;

import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.example.thiscode.security.model.PrincipalUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final String TOKEN = "TOKEN";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("User login successes : {}", authentication.getPrincipal());

        String token = jwtTokenProvider.createToken((PrincipalUser) authentication.getPrincipal());
        Cookie tokenCookie = createTokenCookie(token);
        response.addCookie(tokenCookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    private Cookie createTokenCookie(String token) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        return cookie;
    }

}
