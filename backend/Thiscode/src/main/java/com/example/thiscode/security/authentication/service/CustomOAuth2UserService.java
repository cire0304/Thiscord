package com.example.thiscode.security.authentication.service;

import com.example.thiscode.security.authentication.model.GoogleUser;
import com.example.thiscode.security.authentication.model.PrincipalUser;
import com.example.thiscode.security.authentication.model.ProviderUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.debug("OAuth2User : {}", oAuth2User);

        // TODO: 처음 로그인이라면, 회원가입 진행


        ProviderUser providerUser = null;
        if (clientRegistration.getRegistrationId().equals("google")) {
            providerUser = new GoogleUser(oAuth2User, "", "");
        }

        return new PrincipalUser(providerUser);
    }

}
