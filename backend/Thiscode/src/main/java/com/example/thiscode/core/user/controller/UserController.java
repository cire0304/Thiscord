package com.example.thiscode.core.user.controller;

import com.example.thiscode.core.user.controller.request.SignUpRequest;
import com.example.thiscode.core.user.controller.request.UpdateUserRequest;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.service.UserService;
import com.example.thiscode.core.user.service.dto.UserDetailInfoDto;
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


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid SignUpRequest request) {
        userService.singUp(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/users/me")
    public ResponseEntity<JwtSubject> getUserInfo(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(subject);
    }

    /**
     * Use {@link #getUserInfo(JwtSubject)} instead.
     */
    @GetMapping("/users/me/detail")
    @Deprecated(since = "2021-08-01", forRemoval = true)
    public ResponseEntity<UserDetailInfoDto> getUserDetailInfo(@AuthenticationPrincipal JwtSubject subject) {
        return ResponseEntity.ok(userService.getUserDetailInfo(subject.getId()));
    }

    @PutMapping("/users/me")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal JwtSubject subject,
                                             @RequestBody @Valid UpdateUserRequest request,
                                             HttpServletResponse response) {
        User user = userService.updateUser(subject.getId(), request.getNickname(), request.getIntroduction());

        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String jwtToken = jwtTokenProvider.createJwtToken(principalUser);

        Cookie jwtCookie = CookieUtils.createJwtCookie(jwtToken);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok("success");
    }

}
