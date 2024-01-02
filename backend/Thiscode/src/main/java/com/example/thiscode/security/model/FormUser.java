package com.example.thiscode.security.model;

import com.example.thiscode.core.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public class FormUser implements ProviderUser {

    private User user;

    public FormUser(User user) {
        this.user = user;
    }

    @Override
    public Long getId() {
        return user.getId();
    }

    @Override
    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getUsercode() {
        return user.getUserCode();
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
