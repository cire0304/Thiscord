package com.example.thiscode.security.ajax;

import com.example.thiscode.security.common.CommonAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;


@Slf4j
public class AjaxAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        AjaxUserDto ajaxUserDto = objectMapper.readValue(request.getReader(), AjaxUserDto.class);
        if (StringUtils.isEmpty(ajaxUserDto.getEmail()) || StringUtils.isEmpty(ajaxUserDto.getPassword())) {
            throw new InsufficientAuthenticationException("Email or password is empty");
        }

        CommonAuthenticationToken commonAuthenticationToken = new CommonAuthenticationToken(ajaxUserDto.getEmail(), ajaxUserDto.getPassword());
        return getAuthenticationManager().authenticate(commonAuthenticationToken);
    }

}
