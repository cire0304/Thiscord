package com.example.thiscode.security.service;

import com.example.thiscode.core.repository.UserRepository;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.security.model.GoogleUser;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final String DEFAULT_NICNAME = "사용자";

    // OAuth2Provider 은 구글만 있으므로, 구글만 처리하도록 구현
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        User user = null;
        String email = oAuth2User.getAttribute("email");
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            log.debug("Processing registration...");
            user = userRepository.save(new User(
                    email,
                    "",
                    DEFAULT_NICNAME,
                    oAuth2User.getAttribute("sub")));
        } else {
            user = userOptional.get();
        }

        log.debug("User Login : {}", user);

        ProviderUser providerUser = new GoogleUser(user);
        return new PrincipalUser(providerUser);
    }

}
