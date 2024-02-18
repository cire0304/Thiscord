package com.example.thiscode.security.oauth;

import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.service.UserService;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final UserService userService;
    private final String DEFAULT_NICNAME = "사용자";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
            log.debug("Processing registration...");
            return userService.singUp(email, UUID.randomUUID().toString(), DEFAULT_NICNAME);
        });

        log.debug("User Login : {}", user);

        ProviderUser providerUser = new ProviderUser(user);
        return new PrincipalUser(providerUser);
    }

}
