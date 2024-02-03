package com.example.thiscode.domain.user.controller;

import com.example.thiscode.domain.user.controller.request.SignUpRequest;
import com.example.thiscode.domain.user.controller.request.UpdateUserRequest;
import com.example.thiscode.domain.user.controller.response.UserInfosResponse;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.service.UserService;
import com.example.thiscode.security.jwt.JwtSubject;
import com.example.thiscode.security.jwt.JwtTokenProvider;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import com.example.thiscode.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/users/me")
    public ResponseEntity<JwtSubject> getUserInfos(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(subject);
    }

    // TODO: Write test code
    @PostMapping("/users")
    public ResponseEntity<UserInfosResponse> getUserInfos(@RequestBody List<Long> userId) {
        UserInfosResponse response = new UserInfosResponse(userService.getUserInfos(userId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/me")
    public ResponseEntity<JwtSubject> updateUser(@AuthenticationPrincipal JwtSubject subject,
                                             @RequestBody @Valid UpdateUserRequest request,
                                             HttpServletResponse response) {
        User user = userService.updateUser(subject.getId(), request.getNickname(), request.getIntroduction());

        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String jwtToken = jwtTokenProvider.createJwtToken(principalUser);

        Cookie jwtCookie = CookieUtils.createJwtCookie(jwtToken);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok(new JwtSubject(principalUser));
    }

}
