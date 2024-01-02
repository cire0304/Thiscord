package com.example.thiscode.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    Long getId();
    String getNickname();
    String getPassword();
    String getEmail();
    String getUsercode();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();

}
