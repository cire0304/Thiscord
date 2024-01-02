package com.example.thiscode.security.authentication.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public class GoogleUser implements ProviderUser {

    private OAuth2User oAuth2User;
    private String usercode;
    private String email;


    // TODO: 인자 다시 생각
    // TODO: user 엔티티 개발후에 수정해야함
    public GoogleUser(OAuth2User oAuth2User, String email, String usercode) {
        this.oAuth2User = oAuth2User;
        this.usercode = usercode;
        this.email = email;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getNickname() {
        return "nickname";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getEmail() {
        return "email";
    }

    @Override
    public String getUsercode() {
        return "usercode";
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

}
