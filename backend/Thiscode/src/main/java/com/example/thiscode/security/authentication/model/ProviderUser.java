package com.example.thiscode.security.authentication.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String getId();
    String getNickname();
    String getPassword();
    String getEmail();
    String getUsercode();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();

}
