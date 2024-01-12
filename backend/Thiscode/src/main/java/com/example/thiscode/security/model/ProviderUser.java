package com.example.thiscode.security.model;

import com.example.thiscode.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public class ProviderUser {

    private User user;

    public ProviderUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getUsercode() {
        return user.getUserCode();
    }

    public String getIntroduction() {
        return user.getIntroduction();
    }

    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Map<String, Object> getAttributes() {
        return null;
    }

}
