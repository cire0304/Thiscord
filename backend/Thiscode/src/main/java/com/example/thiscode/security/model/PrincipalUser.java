package com.example.thiscode.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalUser implements UserDetails, OAuth2User {
    private final ProviderUser providerUser;

    public PrincipalUser(ProviderUser providerUser) {
        this.providerUser = providerUser;
    }

    public Long getId() {
        return providerUser.getId();
    }

    public String getUserCode() {
        return providerUser.getUsercode();
    }

    public String getNickname() {
        return providerUser.getNickname();
    }

    public String getEmail() {
        return providerUser.getEmail();
    }

    public String getIntroduction() {
        return providerUser.getIntroduction();
    }

    @Override
    public String getPassword() {
        return providerUser.getPassword();
    }

    @Override
    public String getUsername() {
        return providerUser.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return providerUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return providerUser.getAuthorities();
    }

}
